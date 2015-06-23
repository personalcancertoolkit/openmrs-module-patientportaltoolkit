<% if (relationshipTypes) { %>
<select>
    <% relationshipTypes.each { relationshipType -> %>
    <% if (selectedRelationShiptype) { %>
    <% if (selectedRelationShiptype.equals(relationshipType.aIsToB)) { %>
    <option selected>${(relationshipType.aIsToB)}</option>
    <% } else { %>
    <option>${(relationshipType.aIsToB)}</option>
    <% }%>
    <% }%>
    <% } %>
</select>
<% } %>