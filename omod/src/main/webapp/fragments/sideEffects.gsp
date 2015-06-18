<div>
    <% if (concepts) { %>
    <% concepts.each { %>
  <h3>${ (it.getName()) }</h3>
    <p>${ (it.getDescription()) }</p>
    <% } %>
    <% } else { %>
    There are no side effects for you.
    <% } %>
</div>