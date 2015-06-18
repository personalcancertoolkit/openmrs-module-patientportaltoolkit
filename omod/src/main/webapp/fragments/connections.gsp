<div class="clearfix" id="friends-list">
    <div class="modal fade" id="confirm-friend-delete" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                </div>

                <div class="modal-body">
                    Are you sure that you'd like to remove this friend?
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Go Back</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="delete-btn"
                            delete={this.confirmDelete}>Yes, Remove Friend</button>
                </div>
            </div>
        </div>
    </div>
    <% if (relationships) { %>
    <ul class="media-list">
        <% relationships.each { %>
        <li class="media friendlist-item">

            <div class="panel panel-default">
                <div class="panel-body">
                    <button type="button" class="close" aria-label="remove">
                        <span aria-hidden="true">&times;</span>
                    </button>

                    <div class="media-left">
                        <a href="#">
                            <img class="media-object" src="/openmrs/images/openmrs_logo_white.gif" alt="Default Picture" width="60" height="60"/>
                        </a>
                    </div>

                    <div class="media-body">
                        <h4 class="media-heading">${ (it.getRelationType()) }</h4>
                        <select>
                            <option selected>doctor</option>
                            <option>friend</option>
                            <option>family</option>
                        </select>
                        <select>
                            <option selected>All Connections</option>
                            <option>Personal Connections</option>
                            <option>Physician Connections</option>
                        </select>
                    </div>
                </div>
            </div>
        </li>
    <% } %>
    </ul>
    <% } else { %>
    There are no connections for you yet!
    <% } %>
</div>