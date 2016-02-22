<div class="modal fade modal-wide"  id="markCompleted-modal" role="dialog" aria-labelledby="markCompletedLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editMarkCompletedLabel">Mark Completed</h4>
            </div>

            <div class="modal-body">
                <input id="markCompletedIdHolder" type="hidden" value="">
                <form class="form-inline" role="form"> <label>Mark Completed Date</label>
                    <input class="form-control datetype" id="markCompletedDate" data-date-end-date="0d" type="text" value=""/>
                </form>
                <form class="form-inline" role="form"> <label>Doctor</label>
                    <input class="form-control " id="markCompletedDoctor" type="text" value=""/>
                </form>
                <form class="form-inline" role="form"> <label>Comments</label>
                    <textarea class="form-control" id="markCompletedComments"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveMarkCompletedButton">Save Changes</button>

                </div>
            </div>
        </div>
    </div>
</div>