(function(exports) {
    exports.ScheduleParser = function() {
        this.parse = function(groupName, successCallback) {
            var scheduleUrl = 'http://rasp.tpu.ru/view.php?for=' + groupName + '&weekType=1';

            $.ajax({
                url: scheduleUrl,
                type: 'GET',
                success: function(result) {
                    successCallback(parseResponse(result));
                }
            });
        };
    };

    function parseResponse(result) {
        var parsedNodes = $.parseHTML(result.responseText);
        var scheduleTables = $(parsedNodes).find('.c-table.schedule');
        var classesStart = [];
        var classes = [];
        var bufStart = '';
        scheduleTables.find('.time').each(function() {
            bufStart = $(this).text();
            if (!classesStart.includes(bufStart)) {
                classesStart.push(bufStart);
            }
        });

        scheduleTables.each(function(index) {
            var week = index;
            $(this).find('tr').each(function(index) {
                if (index === 0) {
                    return;
                }
                var timeIndex = index;
                $(this).find('td').each(function (dayIndex) {
                    if (dayIndex === 0) return;
                    var subject = $(this).find('.subject');
                    if (subject.length === 0) return;

                    classes.push({
                        subject: subject.text(),
                        title : subject.attr('title'),
                        type : $(this).find('.lesson-type').text(),
                        room : $(this).find('.room a').text(),
                        teacher : $(this).find('.group-teacher').text().trim(),
                        classStart: classesStart[timeIndex-1],
                        dayIndex: dayIndex - 1,
                        week: week
                    })
                })
            })
        });

        return classes;
    }
}(window));