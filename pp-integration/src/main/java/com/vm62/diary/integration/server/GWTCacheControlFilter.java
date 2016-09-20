package com.vm62.diary.integration.server;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * {@link Filter} to add cache control headers for GWT generated files to ensure
 * that the correct files get cached.
 * <p>
 * If we are ever going to configure cache control rules on an Apache HTTP server, we might
 * need something like this in .htaccess config file, using both mod_expires and mod_headers:
 * <p>
 * <Files *.nocache.*>
 * ExpiresActive on
 * ExpiresDefault "now"
 * Header merge Cache-Control "public, max-age=0, must-revalidate"
 * </Files>
 * <p>
 * <Files *.cache.*>
 * ExpiresActive on
 * ExpiresDefault "now plus 1 year"
 * </Files>
 */
@Singleton
public class GWTCacheControlFilter implements Filter {

    @Override
    public void destroy() {
        /*his method gives the filter an opportunity to clean up any
        resources that are being held (for example, memory, file handles,
        threads) and make sure that any persistent state is synchronized
        with the filter's current state in memory.*/
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        /*Called by the web container to indicate to a filter that it is
          being placed into service*/
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.contains(".nocache.")) {
            Date now = new Date();
            HttpServletResponse httpResponse = getHttpServletResponseWithDateHeaderSet((HttpServletResponse) response, now);
            // one day old
            httpResponse.setDateHeader("Expires", now.getTime() - 86400000L);
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
        } else if (requestURI.contains(".cache.")) {
            Date now = new Date();
            HttpServletResponse httpResponse = getHttpServletResponseWithDateHeaderSet((HttpServletResponse) response, now);
            // expires in one year
            httpResponse.setDateHeader("Expires", now.getTime() + 31536000000L);
            // cache by browser, not by any proxy
            httpResponse.setHeader("Cache-control", "private");
        }

        filterChain.doFilter(request, response);
    }

    private HttpServletResponse getHttpServletResponseWithDateHeaderSet(HttpServletResponse response, Date date) {
        response.setDateHeader("Date", date.getTime());
        return response;
    }
}