
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

    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<link rel="stylesheet"
	href="${ ui.resourceLink("patientportaltoolkit", "styles/bootstrap.min.css") }"
	type="text/css">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/datepicker.css") }"
          type="text/css">
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/styles.css") }"
          type="text/css">
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/patientPortalScripts.js")}"></script>

    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap-datepicker.js") }"></script>

    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/d3.min.js") }"></script>
    <script type="text/javascript"
            src="${ ui.resourceLink("patientportaltoolkit", "/scripts/profilePicturesBadge.js") }"></script>

    <script type="text/javascript">
        jq('.profileBadge').profileBadge();
    </script>

</head>
<div class="navbar navbar-default container">
    <button type="button" class="navbar-toggle" data-toggle="collapse"
            data-target=".navbar-responsive-collapse">
        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
            class="icon-bar"></span>
    </button>
    <div class="navbar-collapse collapse navbar-responsive-collapse">
        <ul class="nav navbar-nav">
            <li id="patientPortalJournals"><a href="journals.page">Posts</a></li>
            <li id="patientPortalNavHome"><a href="home.page">My Medical Profile</a></li>
            <li id="patientPortalConnections"><a href="patientconnections.page">My Connections</a></li>
            </ul>
        <ul class="nav navbar-nav navbar-right">
            <li id="patientPortalUserName"><a href="editprofile.page"><span class="glyphicon glyphicon-user"></span> ${ (username) }</a></li>
            <li><a href="messages.page"><span class="glyphicon glyphicon-envelope"></span></a></li>
            <li><a  href="feedbackform.page"><span class="glyphicon glyphicon-edit"></span></a></li>
            <li><a id="navigationLogout">Logout <span
                    class="glyphicon glyphicon-log-out"></span></a></li>

        </ul>
    </div>
</div>
