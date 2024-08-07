<script>

    jq(document).ready(function() {
        const baseUrl = window.location.href.split("/patientportaltoolkit")[0];
        jq(".connectionlink").click(
            	        function () {
                            document.location.href = baseUrl+'/patientportaltoolkit/home.page?personId='+this.id.split("connectionlink")[1];
            	        });
    });
</script>
<% if (user.getPerson().isPatient()){ %>
<div class="clearfix">
    <div class="button-div pull-right">
        <button type="button" class="btn btn-default pad-left" data-toggle="modal" data-target="#add-relationship-modal">Add Connections</button>
    </div>
</div>
<% } %>
<%
    SHARE_STATUS_PENDING = 0;
    SHARE_STATUS_ACCEPTED = 1;
    SHARE_STATUS_REJECTED = 2;
    SHARE_STATUS_RETIRED = -1;
%>
<div class="clearfix" id="friends-list">
    ${ ui.includeFragment("patientportaltoolkit", "connections/removeRelationship") }
    ${ ui.includeFragment("patientportaltoolkit", "connections/addRelationship") }
    ${ ui.includeFragment("patientportaltoolkit", "connections/editRelationshipModal") }
    <% if (relationships) { %>
    <div>
    <% relationships.each { relationship -> %>
    <ul class="media-list">
        <% if (user.getPerson().isPatient() && relationship.getRelatedPerson()==user.getPerson() && relationship.getPerson().isPatient() && relationship.getShareStatus()==SHARE_STATUS_PENDING){%>
        ${ ui.includeFragment("patientportaltoolkit", "connectionsTwoDirection", [twoDirectionFragmentPerson: relationship.getPerson(), twoDirectionFragmentRelation: relationship, twoDirectionFragmentSide:"bIsToA",twoDirectionFragmentAccepted:false]) }
        <% } %>
        <% if (!user.getPerson().isPatient() && relationship.getShareStatus()== SHARE_STATUS_PENDING){%>
        ${ ui.includeFragment("patientportaltoolkit", "connectionsOneDirection", [oneDirectionFragmentPerson: relationship.getPerson(), oneDirectionFragmentRelation: relationship, oneDirectionFragmentSide:"other",oneDirectionFragmentAccepted:false]) }
        <% } %>
    </ul>
    <% } %>

    <% relationships.each { relationship -> %>
    <ul class="media-list">
    <% if (user.getPerson().isPatient() && relationship.getRelatedPerson()==user.getPerson() && relationship.getPerson().isPatient() && relationship.getShareStatus() == SHARE_STATUS_ACCEPTED){%>
    ${ ui.includeFragment("patientportaltoolkit", "connectionsTwoDirection", [twoDirectionFragmentPerson: relationship.getPerson(), twoDirectionFragmentRelation: relationship, twoDirectionFragmentSide:"bIsToA",twoDirectionFragmentAccepted:true]) }
    <% } %>
    <% if (user.getPerson().isPatient() && relationship.getPerson()==user.getPerson() && relationship.getRelatedPerson().isPatient() && relationship.getShareStatus() == SHARE_STATUS_ACCEPTED){%>
    ${ ui.includeFragment("patientportaltoolkit", "connectionsTwoDirection", [twoDirectionFragmentPerson: relationship.getRelatedPerson(), twoDirectionFragmentRelation: relationship, twoDirectionFragmentSide:"aIsToB",twoDirectionFragmentAccepted:true]) }
    <% } %>
    <% if (user.getPerson().isPatient() && relationship.getPerson()==user.getPerson() && !relationship.getRelatedPerson().isPatient() && relationship.getShareStatus() == SHARE_STATUS_ACCEPTED){%>
    ${ ui.includeFragment("patientportaltoolkit", "connectionsOneDirection", [oneDirectionFragmentPerson: relationship.getRelatedPerson(), oneDirectionFragmentRelation: relationship, oneDirectionFragmentSide:"patient",oneDirectionFragmentAccepted:true]) }
    <% } %>
    <% if (!user.getPerson().isPatient() && relationship.getShareStatus() == SHARE_STATUS_ACCEPTED){%>
    ${ ui.includeFragment("patientportaltoolkit", "connectionsOneDirection", [oneDirectionFragmentPerson: relationship.getPerson(), oneDirectionFragmentRelation: relationship, oneDirectionFragmentSide:"other",oneDirectionFragmentAccepted:true]) }
    <% } %>
</ul>
    <% } %>

    </div>
    <% } else { %>
    There are no connections for you yet!
    <% } %>
</div>
