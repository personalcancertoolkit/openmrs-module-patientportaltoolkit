<!-- About to Expire Modal -->
<div id="sessionAboutToExpireModal" class="modal fade" tabindex="-1" role="dialog" style="z-index: 1000000">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Your Session Will Expire in</h4>
      </div>
      <div class="modal-body">
        <h1 class="text-center"><span class="mins"></span> : <span class="secs"></span></h1>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default dismissButton" data-dismiss="modal">Ignore</button>
        <button type="button" class="btn btn-primary extendButton">Extend Session</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Session Expired Modal -->
<div id="sessionHasExpiredModal" class="modal fade" tabindex="-1" role="dialog" style="z-index: 1000001">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">        
        <h4 class="modal-title">Your Session Has Expired</h4>
      </div>
      <div class="modal-body">
        <p>Sorry. Your session has expired. Please log in again</p>
      </div>
      <div class="modal-footer">
        <a href="/" class="btn btn-primary">Go to Log In</a>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->