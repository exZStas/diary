(function(exports) {
    var HEIGHT_OF_ROW = 37;

    exports.UserEvent = function(options) {
        this.id = options.id;
        this.header = options.header;
        this.startTime = new Date(options.startTime);
        this.endTime = new Date(options.endTime);
        this.description = options.description;
        this.color = options.color;

        this.draw = function(placeholder) {
            var eventBlock;
            var eventHeader;
            var timeText;
            var eventTime;
            var eventDescription;
            var startHours = this.startTime.getHours();
            var startMinutes = this.startTime.getMinutes();
            var eventHeight = 37 * (this.endTime - this.startTime) / (60000 * 60);

            timeText = this.startTime.getHours() +
                ':' +
                (this.startTime.getMinutes() > 9 ? this.startTime.getMinutes() : '0' + this.startTime.getMinutes()) +
                ' - ' +
                this.endTime.getHours() +
                ':' +
                (this.endTime.getMinutes() > 9 ? this.endTime.getMinutes() : '0' + this.endTime.getMinutes());

            eventBlock = $('<div>').addClass('event');
            eventHeader = $('<h1>').text(this.header);
            eventTime = $('<p>').text(timeText);
            eventDescription = $('<p>').text(this.description);

            eventBlock
                .append(eventTime)
                .append(eventHeader)
                .append(eventDescription);

            eventBlock.css({
                'top': (startHours + startMinutes / 60) * HEIGHT_OF_ROW + 2 + 'px',
                'background-color': this.color,
                'height': eventHeight
            });

            placeholder.append(eventBlock);
            this.element = eventBlock;
            this.checkHeight();
            this.setEvents();
        };

        this.setEvents = function() {
            var self = this;
            if (this.hiddenDescription) {
                this.element.click(function() {
                    var element = $(this);
                    var description = element.find('h1 + p');
                    if (self.descriptionShown) {
                        description.slideToggle(500, function() {
                            element.toggleClass('auto-height');
                        });
                        self.descriptionShown = false;
                    } else {
                        description.slideToggle(500);
                        element.toggleClass('auto-height');
                        self.descriptionShown = true;
                    }
                });
            }
        };

        this.checkHeight = function() {
            var eventDescription = this.element.find('h1 + p');

            if (eventDescription.position().top + eventDescription.height() > this.element.outerHeight()) {
                eventDescription.hide();
                this.element.addClass('hidden-description');
                this.hiddenDescription = true;
                this.descriptionShown = false;
            }
        };
    }

}(window));