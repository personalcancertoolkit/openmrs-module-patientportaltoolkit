
<head>
    <title>OpenMRS Patient Portal</title>
    <link rel="shortcut icon" type="image/ico"
	href="/openmrs/images/openmrs-favicon.ico">
    <link rel="icon" type="image/png\"
	href="/openmrs/images/openmrs-favicon.png">
    <script type="text/javascript"
	src="${ ui.resourceLink("patientportaltoolkit", "/scripts/jquery-1.11.1.min.js") }"></script>
    <script type="text/javascript"
	src="${ ui.resourceLink("uicommons", "/scripts/jquery-ui-1.9.2.custom.min.js")}"></script>
    <script type="text/javascript"
	src="${ ui.resourceLink("uicommons", "/scripts/underscore-min.js")}"></script>

    <script type="text/javascript"
	src="${ ui.resourceLink("uicommons", "/scripts/jquery.toastmessage.js")}"></script>
    <script type="text/javascript"
	src="${ ui.resourceLink("uicommons", "/scripts/jquery.simplemodal.1.4.4.min.js")}"></script>
    <script type="text/javascript"
	src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap.min.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-year-calendar.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-switch.min.js") }"></script>
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/bootstrap-switch.min.css") }"
          type="text/css">

    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<link rel="stylesheet"
	href="${ ui.resourceLink("patientportaltoolkit", "styles/bootstrap.min.css") }"
	type="text/css">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/datepicker.css") }"
          type="text/css">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/styles.css")}?v=1"
          type="text/css">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/bootstrap-editable.css") }"
          type="text/css">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/font-awesome.min.css") }"
          type="text/css">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/bootstrap-year-calendar.min.css") }"
          type="text/css">
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/patientPortalScripts.js")}?v=7"></script>

    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-datepicker.js") }"></script>
    
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/profilePicturesBadge.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/jquery.mockjax.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-editable.min.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/sessionTimeoutHandler.js") }"></script>


    <script type="text/javascript">
        jq('.profileBadge').profileBadge();
    </script>

    <style>
        .headerForApp a{
            cursor:pointer;
        }
    </style>
</head>
<div id="uiActivityIndicator"></div>
<div class="navbar navbar-default container headerForApp">
    <button type="button" class="navbar-toggle" data-toggle="collapse"
            data-target=".navbar-responsive-collapse">
        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
            class="icon-bar"></span>
    </button>
    <div class="navbar-collapse collapse navbar-responsive-collapse">
        <ul class="nav navbar-nav">
            <li id="patientPortalJournals" onclick="logEvent('MY_POSTS_MENU_CLICKED','')"> <a href="/openmrs/patientportaltoolkit/journals.page"><span class="fa fa-comments-o  fa-lg"></span> &nbsp; My Posts</a></li>
            <% if(contextUser.person.isPatient()) { %>
                <li id="patientPortalNavHome" onclick="logEvent('MY_MEDICAL_PROFILE_MENU_CLICKED','')"><a href="/openmrs/patientportaltoolkit/home.page"> <span class="fa fa-medkit fa-lg"></span>  &nbsp; My Medical Profile</a></li>
            <% } %>
            <li id="patientPortalConnections" onclick="logEvent('MY_CONNECTIONS_MENU_CLICKED','')"><a href="/openmrs/patientportaltoolkit/patientconnections.page"><span class="fa fa-address-book-o  fa-lg"></span>  &nbsp; My Connections</a></li>
            <% if(contextUser.person.isPatient()) { %>
                <li id="patientPortalMyCancerBuddies" onclick="logEvent('MY_CANCER_BUDDIES_MENU_CLICKED','')"><a href="/openmrs/patientportaltoolkit/myCancerBuddies.page"> <span class="fa fa-users fa-lg" ></span>  &nbsp; My CancerBuddies</a></li>
            <% } %>
            <% if(contextUser.isSuperUser()) { %>
            <li onclick="logEvent('ADD_NEW_PATIENT_CLICKED','')"><a href="/openmrs/patientportaltoolkit/addNewPatient.page"><span class="fa fa-user-plus fa-lg"></span> &nbsp; Add New Patient</a></li>
            <% } %>
            <% if(contextUser.isSuperUser()) { %>
            <li onclick="logEvent('ADD_NEW_TREATMENT_CLICKED','')"><a href="/openmrs/patientportaltoolkit/addNewTreatments.page"><span class="fa fa-medkit fa-lg"></span> &nbsp; Add New Treatment</a></li>
            <% } %>
            </ul>
        <ul class="nav navbar-nav navbar-right">
            <% if(contextUser.isSuperUser()) { %>
            <li><a href="/openmrs/admin/modules/module.list"><span class="fa fa-upload fa-lg"></span> Update Module</a></li>
            <% } %>
            <li id="patientPortalUserName" onclick="logEvent('USER_ACCOUNT_MENU_CLICKED','')"><a href="/openmrs/patientportaltoolkit/editprofile.page"><span class="fa fa-user-circle fa-lg"></span> &nbsp; ${ (username) }</a></li>
            <li onclick="logEvent('MESSAGES_MENU_CLICKED','')"><a href="/openmrs/patientportaltoolkit/messages.page"><span class="fa fa-envelope fa-lg"></span> &nbsp; Messages</a></li>
            <li class="dropdown" onclick="logEvent('HELP_MENU_CLICKED','')">
                <a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="fa fa-life-ring fa-lg"></span>  &nbsp; Help <b class="caret"></b></a>
                <ul class="dropdown-menu" id="menu1">
                  <li onclick="logEvent('VIDEO_WALKTHROUGHS_MENU_CLICKED','')"><a  href="/openmrs/patientportaltoolkit/help/walkthroughs.page"> Video Walk Throughs </a></li>
                  <li class="divider"></li>
                  <li onclick="logEvent('CONTACT_US_MENU_CLICKED','')"><a  href="/openmrs/patientportaltoolkit/help/feedbackform.page"> Contact Us</a></li>
                </ul>
            </li>
            
            <li onclick="logEvent('LOG_OUT_MENU_CLICKED','')"><a id="navigationLogout">Logout &nbsp; <span class="fa fa-sign-out fa-lg"></span>  </a></li>

        </ul>
    </div>
</div>

${ui.includeFragment("patientportaltoolkit", "sessionTimeoutModals")}
