// OpenMRS's default session length appears to be 30 minutes
const SESSION_LENGTH = 30 * 60 * 1000;
const MILLIS_IN_MINUTE = 60000;

// We intend to give users a 5 minute warning 
const WARNING_MINUTES = 5 * 60 * 1000;

const remainingSessionLeft = (sessionStartedAtInMillis) => {
    const elapsedTime = Date.now() - sessionStartedAtInMillis;
    const remainingTimeInMillis = SESSION_LENGTH - elapsedTime;
    return remainingTimeInMillis;
}

const leadingZeroFormatter = (number) => {
    if (number < 0) {
        return '00';
    }
    return number > 9 ? number : `0${number}`;
}

$(document).ready(function() {

    // Grab the page title so that we can restore it later on
    const originalPageTitle = document.title;

    //   Grab the modal that shows the session expiration warning and make sure
    // it doesn't show up immediately
    const sessionAboutToExpireModal = $("#sessionAboutToExpireModal");
    sessionAboutToExpireModal.modal({
        'show': false
    });

    const expireMinsDisplay = sessionAboutToExpireModal.find(".mins");
    const expireSecsDisplay = sessionAboutToExpireModal.find(".secs");
    const extendSessionButton = sessionAboutToExpireModal.find(".extendButton");

    // Grab the modal that notifies the user that their session has expired
    // Also, don't show it immediately
    const sessionHasExpiredModal = $("#sessionHasExpiredModal");
    sessionHasExpiredModal.modal({
        'show': false
    });

    // Keep track of when the session started or was renewed
    let sessStart = Date.now();

    let sessionAboutToExpireWarningShown = false;

    // When the button to extend the session is clicked, trigger an ajax call
    // Calling logEvent is the simplest way to accomplish a session refresh without
    // changing any existing functionality
    extendSessionButton.on('click', () => {
        logEvent('clicked_Extend_Session', '');
    });

    //   
    const resetSessionHandler = () => {
        // reset the timer
        sessStart = Date.now();

        // reset session warning
        sessionAboutToExpireWarningShown = false;
        sessionAboutToExpireModal.modal('hide');

        // Clear the displays so that if the session expiration warning needs to be
        // shown again, it doesn't momentarily show the most recent timeout
        expireMinsDisplay.html('');
        expireSecsDisplay.html('');

        // Reset the page title
        document.title = originalPageTitle;
    }


    //  Every second, check if the session has expired 
    const sessInspector = window.setInterval(() => {
        const millisecondsRemainingInSession = remainingSessionLeft(sessStart);

        const minutesLeft = Math.floor(millisecondsRemainingInSession / MILLIS_IN_MINUTE);
        const secondsLeft = Math.floor((millisecondsRemainingInSession % MILLIS_IN_MINUTE) / 1000);

        const minuteText = leadingZeroFormatter(minutesLeft);
        const secondsText = leadingZeroFormatter(secondsLeft);

        // If the session hasn't expired yet, but is getting close to
        if (millisecondsRemainingInSession < WARNING_MINUTES && millisecondsRemainingInSession > 0) {

            // If the warning has not yet been shown, show it
            if (!sessionAboutToExpireWarningShown) {
                sessionAboutToExpireWarningShown = true;
                sessionAboutToExpireModal.modal('show');
            }

            // Alternate between showing a warning and the normal page title to have
            // a chance at grabbing the user's attention, if they are on a different tab
            if (secondsLeft % 2 == 0) {
                document.title = '🚨 Session expires in ' + minuteText + ':' + secondsText;
            } else {
                document.title = originalPageTitle;
            }
        }

        if (millisecondsRemainingInSession < 0 && sessionHasExpiredModal.is(":hidden")) {
            sessionHasExpiredModal.modal('show');
            sessionAboutToExpireModal.modal('hide');
            document.title = originalPageTitle;
        }

        // Only bother to update the countdown timer when the warning modal is visible
        if (!sessionAboutToExpireModal.is(":hidden")) {
            expireMinsDisplay.html(minuteText);
            expireSecsDisplay.html(secondsText);
        }

    }, 1000);

    // When any ajax request successfully completes anywhere in the application, 
    // the session is automatically refreshed. Recognizing that, we refresh the timer
    $(document).on("ajaxComplete", resetSessionHandler);

});