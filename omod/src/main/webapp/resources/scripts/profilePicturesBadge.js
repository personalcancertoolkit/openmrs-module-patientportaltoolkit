(function ($) {
    $.fn.profileBadge = function (options) {
        var settings = $.extend({
            border: {
                color: '#ddd',
                width: 3
            },
            colors: ['#FF0000', '#000000','#808000','#800080', '#FFD700', '#3CB371', '#D2B48C', '#2F4F4F'],
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