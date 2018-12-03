${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<form id="adminDashCardForm" class="form-horizontal col-xs-12">

            <div id="tabs">
                <ul>
                    <li><a href="#tabs-2">Categorize/Create Side Effect Rules</a></li>
                    <li><a href="#tabs-3">Categorize/Create Followup Rules</a></li>
                    <li><a href="#tabs-4">Preventive Care Guidelines</a></li>
                    <li><a href="#tabs-5">Cancer Community Data</a></li>
                </ul>

                
                <div id="tabs-3">
                        <div class="clearfix">
                            <div id="divGuideLine" style="margin: auto;width: 75%;border: 3px solid black;padding: 10px;">
                                <div class="clearfix">
                                      <b>Categorize/Create followup rules</b> &emsp;&emsp;
                                      
                                      <br />
                                      <br />
                                      <br />
                                </div>

                                <div class="clearfix">
                                    <% GuideLineData.each { guideLineData -> %>
                                             <div>   
                                                <div class="clearfix">
                                                    <div class="pull-left">
                                                        <div>
                                                            <label><b>Concept ID:</b></label>
                                                            <span id="guideLineConceptId${(guideLineData.id)}">${(guideLineData.followupProcedure.conceptId)} </span> (${(guideLineData.followupProcedure.getName().name)})
                                                            <br />

                                                            <label><b>Guideline Name:</b></label>
                                                            <span id="guideLineName${(guideLineData.id)}">${(guideLineData.name)}</span>
                                                            <br />

                                                            <label><b>Follow up TimeLine:</b></label>
                                                            <span id="guideLinefollowupTimeLine${(guideLineData.id)}">${(guideLineData.followupTimline)}</span>
                                                            <br />

                                                            <input id="guideLineConditionSet${(guideLineData.id)}" type="hidden" value=${(guideLineData.guideLineConditionName)}>

                                                        </div>
                                                    </div>
                                                    <div class="pull-right">
                                                        <a id="guideLineEdit${(guideLineData.id)}" class="no-underline-edit fa fa-pencil fa-lg editGuideLineButton"  data-toggle="modal" data-target="#edit-GuideLine-modal"></a>
                                                   </div>
                                                </div>
                                             </div>
                                             <hr />
                                    <% } %>
                                </div>
                            </div>
                            <div>
                              ${ ui.includeFragment("patientportaltoolkit", "editGuideLine")}
                            </div>
                        </div>
                </div>
                
                <div id="tabs-4">
                        <div class="clearfix">
                            <div id="divPreventiveCareGuideLine" style="margin: auto;width: 75%;border: 3px solid black;padding: 10px;">
                                <div class="clearfix">
                                      <b>Preventive Care GuideLines</b> &emsp;&emsp;
                                      <a class="btn btn-primary btn-sm editPCGResourcesButton" id="pcgBtnAdd" style="color: white;" data-toggle="modal" data-target="#edit-PreventiveCareGuideLine-modal">Add</a>
                                      <br />
                                      <br />
                                      <br />
                                </div>

                                <div class="clearfix">
                                    <% preventiveCareGuideLineData.each { pcgData -> %>
                                             <div>   
                                                <div class="clearfix">
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
                                                            <label><b>Follow-Up TimeLine:</b></label>
                                                            <span id="pcgfollowupTimeLine${(pcgData.id)}">${(pcgData.followupTimeLine)}</span>
                                                               
                                                            
                                                        </div>
                                                    </div>
                                                </div>
                                             </div>
                                             <hr />
                                    <% } %>
                                </div>
                            </div>
                            <div>
                              ${ ui.includeFragment("patientportaltoolkit", "editPreventiveCareGuideLine")}
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
                
                <div id="tabs-2">
                    <div class="clearfix">
                        <div id="divSideEffectRuleConceptMap" style="margin: auto;width: 75%;border: 3px solid black;padding: 10px;">
                            <div class="clearfix">

                                <div class="clearfix">
                                    <b>Categorize/Create Side Effect Rules</b>
                                    <br />
                                    <br />
                                </div>
                
                                <div>
                                    <div class="clearfix">
                                       <% SideEffectConceptMapping.each { sideEffectConceptMapData -> %>
                                           <div>   
                                              <div class="clearfix">
                                                 <div class="pull-left">
                                                    <div>
                                                        <label><b>Side Effect Condition Name:</b></label>
                                                        <span id="sideEffectConditionName${(sideEffectConceptMapData.id)}">${(sideEffectConceptMapData.condition)}</span>
                                                         <br />

                                                        <label><b>Side Effect Concepts:</b></label>
                                                        <span id="sideEffectConceptIdName${(sideEffectConceptMapData.id)}">${(sideEffectConceptMapData.conceptIdName)}</span>
                                                        <br />
                                                    </div>
                                                 </div>
                                                 <div class="pull-right">
                                                    <a id="sideEffectEdit${(sideEffectConceptMapData.id)}" class="no-underline-edit fa fa-pencil fa-lg editSideEffectData"  data-toggle="modal" data-target="#edit-SideEffect-modal"></a>
                                                 </div>
                                              </div>
                                           </div>
                                            <hr />
                                       <% } %>
                                    </div>
                                </div>
                             </div>
                        </div>
                        <div>
                            ${ ui.includeFragment("patientportaltoolkit", "editSideEffect")}
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