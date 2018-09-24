${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<form id="adminDashCardForm" class="form-horizontal col-xs-12">

            <div id="tabs">
                <ul>
                    <li><a href="#tabs-1">Categorize Patients</a></li>
                    <li><a href="#tabs-2">Categorize Forms</a></li>
                    <li><a href="#tabs-3">Categorize/Create Followup Rules</a></li>
                    <li><a href="#tabs-4">Categorize/Create Side Effect Rules</a></li>
                    <li><a href="#tabs-5">Cancer Community Data</a></li>
                </ul>
                
                <div id="tabs-1">
                       <div class="clearfix">
                            <div class="pull-left">
                                <h4>Categorize patients</h4>
                            </div>
                        </div>
                </div>
                <div id="tabs-2">
                        <div class="clearfix">
                            <div class="pull-left">
                                <h4>Categorize forms</h4>
                            </div>
                        </div>
                </div>
                <div id="tabs-3">
                        <div class="clearfix">
                            <div class="pull-left">
                                <h4>Categorize/create followup rules </h4>
                            </div>
                        </div>
                </div>

                <div id="tabs-5">
                        <div class="clearfix">
                            <div id="divCategorizeCommunitiesData" class="pull-left">
                                <div>
                                    <div class="clearfix">
                                        <h4>Cancer Community Data &emsp;</h4>
                                    </div>
                                    <br />
                                </div>
                                
                                <div id="accordion">
                                    <% CancerCommunityData.each { cancerCommunity -> %>
                                       <h3>${(cancerCommunity.cancerType)}</h3>
                                        <div>
                                            <div class="clearfix">
                                                <label><b>Cancer Type:</b></label>
                                                <label> <span id="cancerType${(cancerCommunity.id)}">${(cancerCommunity.cancerType)}</span> </label>
                                                    <div class="pull-right">
                                                        <a id="${(cancerCommunity.id)}" class="no-underline-edit fa fa-pencil fa-lg editCancerCommunityDataresourcesButton"  data-toggle="modal" data-target="#edit-CancerCommunityResourcesData-modal"></a>
                                                     </div>
                                            </div>
                                    
                                            <div class="clearfix">
                                               <div class="pull-left">
                                                  <div>
                                                     <span id="usefulContacts${(cancerCommunity.id)}">${(cancerCommunity.usefulContacts)}</span>
                                                  </div>
                                                  <div>
                                                    <span id="resources${(cancerCommunity.id)}">${(cancerCommunity.resources)}</span>
                                                  </div>
                                               </div>
                                            </div>
                                        </div>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                        <div>
                            ${ ui.includeFragment("patientportaltoolkit", "editCancerCommunityResources")}
                        </div>
                </div>



                <div id="tabs-4">
                        <div class="clearfix">
                            <div id="divPreventiveCareGuideLine" style="margin: auto;width: 75%;border: 3px solid black;padding: 10px;">
                                <div class="clearfix">
                                      <b>Categorize/Create preventive Care Rules</b> &emsp;&emsp;
                                      <a class="btn btn-primary btn-sm editPCGResourcesButton" id="pcgBtnAdd" style="color: white;" data-toggle="modal" data-target="#edit-PreventiveCareGuideLine-modal">Add</a>
                                      <br />
                                      <br />
                                      <br />
                                </div>

                                <div class="clearfix">
                                    <% preventiveCareGuideLineData.each { pcgData -> %>
                                             <div>   
                                                <div class="clearfix">
                                                   <div class="pull-left">
                                                      <div>
                                                           <label><b>Cancer Type:</b></label>
                                                               <% CancerCommunityData.each { pcgCancerCommunity -> %>
                                                                    <% if (pcgCancerCommunity.id== pcgData.cancerTypeId) { %>
                                                                        <input id="pcgCancerID${(pcgData.id)}" type="hidden" value=${(pcgData.cancerTypeId)}>
                                                                        <span id="pcgCancerType${(pcgData.id)}">${(pcgCancerCommunity.cancerType)}</span>
                                                                    <% } %>
                                                                <% } %>
                                                      </div>
                                                   </div>
                                                   <div class="pull-right">
                                                        <a id="pcgEdit${(pcgData.id)}" class="no-underline-edit fa fa-pencil fa-lg editPCGResourcesButton"  data-toggle="modal" data-target="#edit-PreventiveCareGuideLine-modal"></a>
                                                   </div>
                                                </div>
                                                
                                                <div class="clearfix">
                                                    <div class="pull-left">
                                                        <div>
                                                            <label><b>Prevetice Care Guideline Name:</b></label>
                                                            <span id="pcgName${(pcgData.id)}">${(pcgData.name)}</span>

                                                            <br />
                                                            <label><b>No. of Intervals:</b></label>
                                                            <span id="pcgNoOfInteval${(pcgData.id)}">${(pcgData.noOfInterval)}</span>
                                                               
                                                            <br />
                                                            <label><b>Interval Length (in months):</b></label>
                                                            <span id="pcgIntervalLength${(pcgData.id)}">${(pcgData.intervalLength)}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                             <div>
                                             <hr />
                                    <% } %>
                                </div>
                            </div>
                            <div>
                              ${ ui.includeFragment("patientportaltoolkit", "editPreventiveCareGuideLine")}
                            </div>
                        </div>
                </div>

                
            </div>
</form>

<script>
    jq( function() {
        jq("#accordion").accordion();
    });
    
    jq( function() {
        jq("#tabs").tabs({
          collapsible: true
        });
    });
</script>