<% if (relationshipTypes) { %>
<select class="form-control" id="relationshipSelect${(parentForm)}">
    <% relationshipTypes.each { relationshipType -> %>
    <% if (selectedRelationShiptype) { %>
    <% if (selectedRelationShiptype.equals(relationshipType.aIsToB)) { %>
    <option selected>${(relationshipType.aIsToB)}</option>
    <% } else { %>
    <option>${(relationshipType.aIsToB)}</option>
    <% }%>
    <% } else {%>
    <option>${(relationshipType.aIsToB)}</option>
    <% } %>
    <% } %>
</select>
<% } %>