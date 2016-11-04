package TestRunner;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.vm62.diary.common.utils.InjectorProvider;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TestRunner extends BlockJUnit4ClassRunner {
    private Injector injector;

    @Inject
    private EntityManager em;

    @Override
    public Object createTest() throws Exception {
        Object obj = super.createTest();
        injector.injectMembers(obj);
        return obj;
    }

    public TestRunner(Class<?> klass) throws InitializationError {
        super(klass);

        injector = Guice.createInjector(new JpaPersistModule("TestPersistenceUnit"));
        injector.getInstance(PersistService.class).start();
        try {
            InjectorProvider.init(injector);
        } catch (Exception doNothing) {
        }

        injector.injectMembers(this);
    }

    @Override
    public Statement methodBlock(final FrameworkMethod method) {
        em.getTransaction().begin();

        final Statement statement = super.methodBlock(method);
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } finally {
                    em.getTransaction().rollback();
                }
            }
        };

    }
}
