<div>
    <% if (concepts) { %>
    <% concepts.each { %>
  <h3>${ (it.name) }</h3>
    <p>${ (it.description) }</p>
    <% } %>
    <% } else { %>
    There are no side effects for you.
    <% } %>
</div>