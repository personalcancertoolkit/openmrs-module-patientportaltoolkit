<% if (searchPersons) { %>
<ul>
<% searchPersons.each { searchPerson -> %>
<li> ${ (searchPerson.getGivenName()) }
   </li>
    <% } %>
</ul>
<% } %>