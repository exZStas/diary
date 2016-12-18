(function(exports) {
    exports.ScheduleRenderer = function(placeholder) {

        this.draw = function(scheduleData) {
            var date = new Date(scheduleData.date);
            var headerText = date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear();
            this.drawGrid(headerText);
            this.drawEvents(scheduleData.events);
        };

        this.drawGrid = function(headerText) {
            var sheet = $('<div>').addClass('schedule__sheet');
            var header = $('<div>').addClass('schedule__header');
            var content = $('<div>').addClass('schedule__content');
            var dividersBlock = $('<div>');
            var eventsBlock = $('<div>').addClass('events');

            header.text(headerText);

            var bufDivider;
            var timeSpan;
            var timeText;

            for (var i = 0; i < 24; i++) {
                bufDivider = $('<div>').addClass('divider');
                timeSpan = $('<span>');
                timeText = i < 10 ? '0' + i + ':00': i + ':00';

                timeSpan.append(timeText);
                bufDivider.append(timeSpan);
                dividersBlock.append(bufDivider);
            }

            content.append(dividersBlock);
            content.append(eventsBlock);
            sheet.append(header);
            sheet.append(content);

            placeholder.empty();
            placeholder.append(sheet);

            this.eventContainer = eventsBlock;
        };

        this.drawEvents = function(events) {
            var bufEvent;
            for (var i = 0; i < events.length; i++) {
                bufEvent = new UserEvent(events[i]);
                bufEvent.draw(this.eventContainer);
            }

        }
    };
}(window));