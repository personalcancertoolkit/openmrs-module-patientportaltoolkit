<div class="modal fade modal-wide"  id="markScheduled-modal" role="dialog" aria-labelledby="markScheduledLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editMarkScheduledLabel">Mark Scheduled</h4>
            </div>

            <div class="modal-body">
                <input id="markScheduledIdHolder" type="hidden" value="">
                <form class="form-inline" role="form"> <label>Mark Scheduled Date</label>
                    <input class="form-control datetype" id="markScheduledDate" type="text" value=""/>
                </form>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveMarkScheduledButton">Save Changes</button>
                </div>
            </div>
        </div>
    </div>
</div>