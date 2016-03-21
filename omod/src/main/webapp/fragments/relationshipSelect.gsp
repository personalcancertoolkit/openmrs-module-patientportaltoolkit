<% if (relationshipTypes) { %>
<select class="form-control" id="relationshipSelect${(parentForm)}">
    <% relationshipTypes.each { relationshipType -> %>
    <% if (selectedRelationShiptype) { %>
    <% if (selectedRelationShiptype.equals(relationshipType.aIsToB)) { %>
    <option selected value="${(relationshipType.getUuid())}">${(relationshipType.aIsToB)}</option>
    <% } else { %>
    <option value="${(relationshipType.getUuid())}">${(relationshipType.aIsToB)}</option>
    <% }%>
    <% } else {%>
    <option value="${(relationshipType.getUuid())}">${(relationshipType.aIsToB)}</option>
    <% } %>
    <% } %>
</select>
<% } %>