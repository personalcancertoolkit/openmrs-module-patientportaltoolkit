(function ($) {
    $.fn.profileBadge = function (options) {
        var settings = $.extend({
            border: {
                color: '#ddd',
                width: 3
            },
            colors: ['#a3a948', '#edb92e', '#f85931', '#ce1836', '#009989'],
            text: '#fff',
            size: 60,
            margin: 0,
            middlename: true,
            uppercase: true
        }, options);
        return this.each(function () {
            var elementText = $(this).text();
            var initialLetters = elementText.match(settings.middlename ? /\b(\w)/g : /^\w|\b\w(?=\S+$)/g);
            var initials = initialLetters.join('');
            $(this).text(initials);
            $(this).css({
                'color': settings.text,
                'background-color': settings.colors[Math.floor(Math.random() * settings.colors.length)],
                'border': settings.border.width + 'px solid ' + settings.border.color,
                'display': 'inline-block',
                'font-family': 'Arial, \'Helvetica Neue\', Helvetica, sans-serif',
                'font-size': settings.size * 0.4,
                'border-radius': settings.size + 'px',
                'width': settings.size + 'px',
                'height': settings.size + 'px',
                'line-height': settings.size + 'px',
                'margin': settings.margin + 'px',
                'text-align': 'center',
                'text-transform' : settings.uppercase ? 'uppercase' : ''
            });
        });
    };
}(jQuery));