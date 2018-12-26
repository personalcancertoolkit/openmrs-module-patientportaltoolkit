
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
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-year-calendar.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-switch.min.js") }"></script>
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/bootstrap-switch.min.css") }"
          type="text/css">

    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">

    <link href="https://stackpath.bootstrapcdn.com/bootswatch/3.3.7/flatly/bootstrap.min.css" rel="stylesheet" integrity="sha384-+ENW/yibaokMnme+vBLnHMphUYxHs34h9lpdbSLuAwGkOKFRl4C34WkjazBtb7eT" crossorigin="anonymous">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/datepicker.css") }"
          type="text/css">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/styles.css") }"
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
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/formio.full.min.css") }"
          type="text/css">
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/patientPortalScripts.js")}"></script>

    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-datepicker.js") }"></script>
    
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/profilePicturesBadge.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/jquery.mockjax.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-editable.min.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/formio.full.min.js") }"></script>

    <script type="text/javascript">
        jq('.profileBadge').profileBadge();
    </script>

    <style>
        .headerForApp a{
            cursor:pointer;
        }
    </style>
</head>
<div class="navbar navbar-default container headerForApp">
    <button type="button" class="navbar-toggle" data-toggle="collapse"
            data-target=".navbar-responsive-collapse">
        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
            class="icon-bar"></span>
    </button>
    <div class="navbar-collapse collapse navbar-responsive-collapse">
        <ul class="nav navbar-nav">
            <li id="patientPortalJournals"> <a href="/openmrs/patientportaltoolkit/journals.page"><i class="material-icons">forum</i> My Posts</a></li>
            <% if(contextUser.person.isPatient()) { %>
                <li id="patientPortalNavHome"><a href="/openmrs/patientportaltoolkit/home.page"> <i class="material-icons">local_hospital</i> My Medical Profile</a></li>
            <% } %>
            <li id="patientPortalConnections"><a href="/openmrs/patientportaltoolkit/patientconnections.page"><i class="material-icons">perm_contact_calendar</i> My Connections</a></li>
            <% if(contextUser.person.isPatient()) { %>
                <li id="patientPortalMyCancerBuddies"><a href="/openmrs/patientportaltoolkit/myCancerBuddies.page"><i class="material-icons">group</i> My CancerBuddies</a></li>
            <% } %>
            <% if(isAdmin) { %>
                <li id="patientPortalAdminDashBoard"><a href="/openmrs/patientportaltoolkit/adminDashBoard.page"><i class="material-icons">dashboard</i> Admin DashBoard</a></li>
            <% } %>

            </ul>
        <ul class="nav navbar-nav navbar-right">
            <li id="patientPortalUserName"><a href="/openmrs/patientportaltoolkit/editprofile.page"><i class="material-icons">face</i> ${ (username) }</a></li>
            <li><a href="/openmrs/patientportaltoolkit/messages.page"><i class="material-icons">email</i>  Messages</a></li>
            
            <li class="dropdown">
                <a href="#" data-toggle="dropdown" class="dropdown-toggle"><i class="material-icons">help</i> Help <b class="caret"></b></a>
                <ul class="dropdown-menu" id="menu1">
                  <li><a  href="/openmrs/patientportaltoolkit/help/walkthroughs.page"> Video Walk Throughs </a></li>
                  <li class="divider"></li>
                  <li><a  href="/openmrs/patientportaltoolkit/help/feedbackform.page"> Contact Us</a></li>
                </ul>
            </li>
            
            <li><a id="navigationLogout">Logout <i class="material-icons">exit_to_app</i></span>  </a></li>

        </ul>
    </div>
</div>
