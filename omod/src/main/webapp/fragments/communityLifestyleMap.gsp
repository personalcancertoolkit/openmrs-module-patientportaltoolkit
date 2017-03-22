<script>
var communities_map_handler = {
    ///////////////
    // Public Properties 
    ///////////////
    search_term : null,
    
    ///////////////
    // Private` Properties (` => private other than for initialization)
    ///////////////
    // DOM Elements
    map_element : null,
    //Google API Objects
    map_object : null,
    infowindow : null, 
    places_search_service : null,
    // Place Processing Data
    mapped_places : [],         // Array to store mapped places
    marker_list : [],           // Array to store marker references
    places_waiting_to_be_listed : 0, // Used to track when list can be drawn, as map_this_place() is async
    draw_full_list_timeout : null, // Used to track when list can be drawn, in conjunction with above variable
    
    
    ///////////////////////////////////////////////////////////
    // Service and Map initialization
    ///////////////////////////////////////////////////////////
    initialize_map : function() {
        // Define and initialize the map object
        this.map_object = new google.maps.Map(this.map_element, {
            center: this.current_position,
            zoom: this.current_zoom,
        });
        this.map_object.panTo(this.current_position);
        
        // Define and initialize infowindow object
		this.infowindow = new google.maps.InfoWindow();
        
        // Define and initialize service
		this.places_search_service = new google.maps.places.PlacesService(this.map_object);
		
        // Run initial search
        // setTimeout(this.run_a_search.bind(this), 300); // set timeout, otherwise this.map_object.getBounds() will not be defined when needed.
				  
        // Set listener to re-search any time map boundaries are changed. Note: this will fire when map loads.
		this.map_object.addListener('bounds_changed',  this.run_a_search.bind(this));
    },
    
    ///////////////////////////////////////////////////////////
    // Search Methods
    ///////////////////////////////////////////////////////////
    run_a_search : function(){
		var request = this.build_search_request_for_term(this.search_term);	
		this.places_search_service.nearbySearch(request, this.search_callback_function.bind(this));
    },
    
    search_callback_function : function(results, status){
        if(status === google.maps.places.PlacesServiceStatus.OK) {
            for (var i = 0; i < results.length; i++){
                var this_place = results[i];
                var bool_yes = this.should_this_place_be_mapped(this_place);
                if (bool_yes) this.map_this_place(this_place);
            }
			this.draw_full_list(); 
        }
    },
	build_search_request_for_term : function(search_term){
        //console.log(search_term);
        var request = {
            bounds: this.map_object.getBounds(),
            name: search_term,
        };
        if(search_term == "fitness"){
            request = {
                bounds: this.map_object.getBounds(),
                type:["gym"],
            };
        }
        if(this.map_object.getBounds() == undefined){
            request.center = this.current_position;   
        }
		return request;	
	},
    
    
    /////////////////////////////////////////////////////////////
    // Search Result Response Methods
    /////////////////////////////////////////////////////////////
    should_this_place_be_mapped : function(this_place){
        if(this_place.permanently_closed) return false; // If this place is permanently closed, do not map it.
        var mapped_place_ids = this.mapped_places.map(function(a) {return a.place_id;}); // return list of id's for all mapped places
        if(mapped_place_ids.indexOf(this_place.place_id) != -1) return false; // If this place's id is in mapped_place_ids then we have already mapped it.
        if (this.search_term == "farmers markets"){ 
            var place_score = this.rank_farmers_market(this_place); // rank this place 
            if (place_score <= 1) return false; // if score is too low, dont map it.
        }
        return true;
	},   
    map_this_place : function(this_place){
        // Get details for this place, then with details we can map it.
        var request = { placeId: this_place.place_id, };
        var callback = function(place, status){ this.mark_place_on_map(place)}.bind(this);
		this.places_search_service.getDetails(request, callback);
        this.places_waiting_to_be_listed += 1; // required as places_search_service.getDetails is async
    },
    mark_place_on_map : function(this_place){
        if(this_place == null){
            this.places_waiting_to_be_listed -= 1; // required as places_search_service.getDetails is async
            return;
        }
        
        // Create and place marker on map
        var marker = new google.maps.Marker({
            map: this.map_object,
            position: this_place.geometry.location
        });

        
        // get listing html for place
        var list_html = this.return_list_html(this_place); 
        
        
        // Record marker and place with markerIindex and list_html
        this.marker_list.push(marker); //add marker to a list so there is a reference to it later
        this_place.marker_index = this.marker_list.length-1;
        this_place.list_html = list_html;
		this.mapped_places.push(this_place);
        //console.log('added a place');
        

        this.places_waiting_to_be_listed -= 1; // required as places_search_service.getDetails is async
        return;
        google.maps.event.addListener(marker, 'click', function() {
            var rating = (place.rating) ? place.rating : ''; // ternary 
            var html = build_info_content(place.name, place.place_id, rating);  
            infowindow.setContent(html);
            infowindow.open(map, this);
        });// end addListener

        draw_listings();
    },
    rank_farmers_market : function(this_place){
		var place_score = 0;
		if(this_place.types){
			for (var i=0;i<this_place.types.length;i++){
				switch (this_place.types[i].toLowerCase()){
					case "bakery":
					case "restaurant": place_score++;break;
					case "food":
					case "grocery_or_supermarket":
					case "store":place_score+=2;break;
					case "campground":
					case "local_government_office":place_score=-1;break;
					case "travel_agency":
					case "real_estate_agency":
					case "insurance_agency":
					case "beauty salon":
					case "finance":
					case "bar":place_score = -10;break;
				}//switch
			}//for
		}//if(this_place.types)					
		return place_score;
	},    
    
    ////////////////////////////////////////////////////////////////////////
    // Result Listings Methods
    /////////////////////////////////////////////////////////////////////////
	return_list_html : function(this_place){
            // get biz_type
			biz_type = (this_place.types) ? this_place.types[0].toUpperCase() : ''; // ternary 
			
			//get short address
			var short_address = (this_place.address_components) ? this.build_the_address(this_place.address_components) : ''; // ternary 

            //get the stars img
			var stars_text = (this_place.rating) ? this.get_the_stars(this_place.rating) : ''; // ternary 
			
			//get open_now
            var open_now = '';
			if(this_place.opening_hours){
                if(this_place.opening_hours.open_now) open_now += 'Open Now - ';
                var today = new Date();
                var day_num = today.getDay();
                if(this_place.opening_hours.weekday_text){
                    var open_hours = this_place.opening_hours.weekday_text[day_num].split(" ");
                    var open_message = '';
                    if(open_hours.length > 1){
                        for (var i=1;i<open_hours.length;i++){
                            open_message += " "+open_hours[i];
                        }
                        open_now += open_message;
                    }
                }
			}
		
            // Generate new html from template.
            var div = document.createElement('div');
            div.innerHTML = this.template_html;
            /*
            <div class="list_item" id="">
                <h5>
                    <a href="javascript:show_listing( \''+place_id+'\');">'+this_place.name+'</a>
                </h5>
                <p class="small-line-height">
                    <a href="javascript:show_listing( \''+place_id+'\');">'  +short_address+'</a>
                </p>
                <small style = 'font-weight:bold'>
                    <a href="#detail_modal" data-toggle="modal" data-place_id="'+place_id+'"> SEE MORE DETAILS...</a>
                </small>
                <p class="small-line-height">'+open_now+'</p>
            </div>
            */
            var main_holder = div.getElementsByClassName('list_item')[0];
            main_holder.id = this_place.place_id;
            var anchors = div.getElementsByTagName('a');
            anchors[0].href = 'javascript:communities_map_handler.show_listing("'+this_place.place_id+'");';
            anchors[0].innerHTML = this_place.name;
            anchors[1].href = 'javascript:communities_map_handler.show_listing("'+this_place.place_id+'");';
            anchors[1].innerHTML = short_address;
            //anchors[2].href = 'javascript:';
            anchors[2].id = 'modal_button_for_place_' + this_place.place_id; 
            anchors[2].onclick = function(){communities_map_handler.show_listing(this_place.place_id); };
            var paragraphs = div.getElementsByTagName('p');
            paragraphs[1].innerHTML = open_now;

            var list_html = div.innerHTML;
            //document.removeChild(div);
        
            return list_html;
	},
    
    
    draw_full_list : function(){
        if(this.places_waiting_to_be_listed > 0){ // required as places_search_service.getDetails is async
            //console.log('here i am' + this.places_waiting_to_be_listed);
            if(this.draw_full_list_timeout !== null) clearTimeout(this.draw_full_list_timeout);  
            this.draw_full_list_timeout = setTimeout(this.draw_full_list.bind(this), 500); // checkback in half a second to see if all places have received responses.
            return;
        }
        //console.log('drawing list');
        
        
        // Empty Last List
        this.list_element.empty();
        this.fitness_tab_element.removeClass("active");
        this.farmers_tab_element.removeClass("active");
        
        // Generate Header
        if(this.search_term == "farmers markets"){
            var this_name = "Farmer's Markets";
            this.farmers_tab_element.addClass("active");
        } else if(this.search_term == "fitness"){
            var this_name = "Fitness Centers";
            this.fitness_tab_element.addClass("active");
        } else {
            var this_name = "Places of Interest";   
        }
        this.list_title_element.html(this_name);
        /*
            jq("#details").empty();
            jq("#details").html("<h3>More Information</h3><p>Please select a location from the map or from the list for more information.</p>");
            //clear out the #test div
            jq("#test").empty();
        */
        
        //console.log('here i am!');
        //console.log(this.mapped_places);
        // Generate Listings
        this.sort_places_by_distance();
        for(var i = 0; i < this.mapped_places.length; i++){
            this.list_element.append(this.mapped_places[i].list_html);   
        }
        
        // Scroll to selected listing if not visible at top.
		jq("#venue_list").scrollTop(0);
        var selected_item = jq(".hilite_item")[0];
		if(selected_item){
			var item_top = selected_item.position().top;
			if (item_top > jq("#venue_list").height()){
				jq("#venue_list").scrollTop(item_top);
			}
		}
        
    },
    
    euclidean_distance : function(v1, v2){
        // V1 and V2 are to be arrays/lists, both of d dimensions
        if(v1.length !== v2.length) console.error('Euclidean distance measure requires that both vectors are of equal dimensions'); 
        var summed_distance_squared = 0;
        for(var i = 0; i < v1.length; i++){
            var distance = v1[i] - v2[i];
            var squared = Math.pow(distance, 2);
            summed_distance_squared += squared;
        }
        return Math.pow(summed_distance_squared, 0.5);
    },
    
    sort_places_by_distance : function(){
		this.mapped_places.sort(function(a,b){
            var position_vector = [this.current_position.lat, this.current_position.lng];
            var vector_A = [a.geometry.location.lat(), a.geometry.location.lng()];
            var vector_B = [b.geometry.location.lat(), b.geometry.location.lng()];
            //console.log(position_vector);
            //console.log(vector_A);
            //console.log(vector_B);
            
            var distanceA = this.euclidean_distance(position_vector, vector_A);
            var distanceB = this.euclidean_distance(position_vector, vector_B);
            //console.log(distanceA);
            //console.log(distanceB);
            
			if (distanceA < distanceB){return -1;} //sort string ascending
			if (distanceA > distanceB){return 1;}
 			return 0; //default return value (no sorting)	
		}.bind(this)); //placeList.sort(function(a,b)
    },
    
	get_the_stars : function(rating){
        return "<small>Rating : </small>" +rating + "/5 ";
		var stars;
		switch (true){
			case ( rating <  0.25)					  :stars = "img/0_0_stars.png";break;
			case ((rating >= 0.25) && (rating < 0.75)):stars = "img/0_5_stars.png";break;
			case ((rating >= 0.75) && (rating < 1.25)):stars = "img/1_0_stars.png";break;
			case ((rating >= 1.25) && (rating < 1.75)):stars = "img/1_5_stars.png";break;
			case ((rating >= 1.75) && (rating < 2.25)):stars = "img/2_0_stars.png";break;
			case ((rating >= 2.25) && (rating < 2.75)):stars = "img/2_5_stars.png";break;
			case ((rating >= 2.75) && (rating < 3.25)):stars = "img/3_0_stars.png";break;
			case ((rating >= 3.25) && (rating < 3.75)):stars = "img/3_5_stars.png";break;
			case ((rating >= 3.75) && (rating < 4.25)):stars = "img/4_0_stars.png";break;
			case ((rating >= 4.25) && (rating < 4.75)):stars = "img/4_5_stars.png";break;
			case  (rating >= 4.75)				      :stars = "img/5_0_stars.png";break;
			default									  :stars = "img/no_stars.png" ;break;
		}//end switch
        star_html =  '<small>RATING: </small><img src="'+stars+'"/>&emsp;&emsp;';
		return star_html;
	},
    
	build_the_address : function(address_parts){
		var address = "";
		address  = address_parts[0].short_name+ ' ';  	// street number
		address += address_parts[1].short_name+', ';	// street	
		address += address_parts[2].short_name+', ';	// city
		address += address_parts[3].short_name;			// state
		return address; 
    },
    
    //////////////////////////////////////////////////////////
    // List and Modal Helpers
    //////////////////////////////////////////////////////////
    show_listing : function(place_id){
			var place = this.get_place_by_id(place_id);
			var marker_index = place.marker_index;
			//center the map
			var placeLoc = place.geometry.location;
			this.map_object.panTo(placeLoc);
			//get markup for infoWindow content
			var rating = '';
			if ( place.rating ) {rating = place.rating;}
			var html = this.build_info_content(place.name, place_id, rating);
			this.infowindow.setContent(html);
			this.infowindow.open(this.map_object, this.marker_list[marker_index]);
			
    },
    

	hilite_list_item : function(this_id){
		jq(".hilite_item").removeClass('hilite_item');
        jq("#"+this_id).addClass('hilite_item');
	},
    build_info_content : function(placename,place_id,rating){
        //console.log(rating);
        var stars_text = (rating) ? this.get_the_stars(rating) : ''; // ternary 
        var details_text = 'SEE MORE DETAILS';

        this.hilite_list_item(place_id);
        var html='';
        html = '<div><b>'+placename+'</b></div><div style="clear:both;float:left;overflow:hidden;">'+stars_text+'</div><Br><div style="overflow:hidden;"><small><b><a href="#detail_modal" data-toggle="modal" id="modal_button_for_place_'+place_id+'">'+details_text+'</a></b></div>';
        return html;
    },
	draw_modal_body : function(this_place){	
        var modal_markup = '';
        var hours = '<p>No Business Hours Information Available</p>';
        var weekdays;
        var website_text = '';
        var review_text = '<p>There are no reviews.</p>';
		if(this_place.opening_hours){  //objects must be tested at each level
			if(this_place.opening_hours.weekday_text){
				weekdays = this_place.opening_hours.weekday_text;
				hours = '<b>Hours</b><br/>';
				for (var i=0;i<weekdays.length;i++){
					hours += weekdays[i]+'<br/>';
				}
				hours += '</p>';
			}//end this_place.weekday_text
			if(this_place.opening_hours.open_now){hours += '<p><b>Open Now</b></p>';}
		}//end  if(this_place.opening_hours.weekday_text)
		if(this_place.website){ 
			website_text = '<p><b>Website:</b> <a href="'+this_place.website+'" target="_blank">'+this_place.website+'</a></p>'; 
		}
		if(this_place.reviews){
			var num_reviews = this_place.reviews.length;
			switch (num_reviews){
				case 0:break;
				case 1:review_text = '<p>There is '+num_reviews+' review.';break;
				default:review_text = '<p>There are '+num_reviews+' reviews.';break;
			}//switch
		}//if
		
		modal_markup = 	hours+website_text+review_text;
		return modal_markup;
	},
    get_place_by_id : function(place_id){
        for(var i=0;i<this.mapped_places.length;i++){
            if (this.mapped_places[i].place_id == place_id){return this.mapped_places[i];}
        }
    },
    
}



window.addEventListener("load", function(){
    
    // Initialize communities_map_handler
    // Data
    communities_map_handler.current_position = {lat:39.768549, lng:-86.158008};	//Starting at Monument Circle
    communities_map_handler.current_zoom = 12;	
    communities_map_handler.search_term = 'fitness';	
    communities_map_handler.template_html = document.getElementById('list_element_example').innerHTML;
    // DOM
    communities_map_handler.map_element = document.getElementById('map_element');
    communities_map_handler.list_element = jq("#venue_list");
    communities_map_handler.list_title_element = jq("#venue_list_title");
    communities_map_handler.fitness_tab_element = jq("#fit-tab");
    communities_map_handler.farmers_tab_element = jq("#fm-tab");
    
    // Load google maps only after page has loaded. Dont slow down page load and proprly initialize communities_map_handler first.
    var script = document.createElement('script');
    var maps_APIKEY = '${CommunitiesMapsAPIKEY}';
    script.type = 'text/javascript';
    // NOTE:  The call to googleapis must include "&libraries=places"
    script.src = 'https://maps.googleapis.com/maps/api/js?callback=communities_map_handler.initialize_map&libraries=places&key=' + maps_APIKEY;
    document.body.appendChild(script);
    
    
    // Initialize show details module
    jq('#detail_modal').on('show.bs.modal', function (event) {
        var link = jq(event.relatedTarget); // Link that triggered the modal
        var place_id = link[0].id.split('modal_button_for_place_')[1]; // Extract info from data-* attributes
        var place = communities_map_handler.get_place_by_id(place_id);
        var modal = jq(this);
        var modal_markup = communities_map_handler.draw_modal_body(place);
        modal.find('.modal-body').html(modal_markup); 
        modal.find('.modal-title').html(place.name); 
    })//end "on" function
});

</script>


<!-- BEGIN Bootstrap Modal for Place Details -->
<div id="detail_modal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
        <p>No Information Available.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- END Bootstrap Modal --> 

<div id = 'list_element_example' style = 'display:none'>
    <div class="list_item" id="">
        <h5>
            <a href="javascript:show_listing( \''+place_id+'\');">'+this_place.name+'</a>
        </h5>
        <p class="small-line-height">
            <a href="javascript:show_listing( \''+place_id+'\');">'  +short_address+'</a>
        </p>
        <small style = 'font-weight:bold'>
            <a href="#detail_modal" data-toggle="modal" id = ''> SEE MORE DETAILS...</a>
        </small>
        <p class="small-line-height">'+open_now+'</p>
    </div>
</div>

<div class="container">
    <ul id="map-nav" class="nav nav-tabs">
        <li id="fit-tab" role="presentation" class="active"><a href="javascript:get_venues('fitness');" aria-controls="fitness" role="tab">Fitness Centers</a></li>
        <li id="fm-tab" role="presentation"><a href="javascript:get_venues('farmers market');" aria-controls="farmers_market" role="tab">Farmers Markets</a></li>
    </ul>
    <div class="row">
        <div class="col-md-5">
            <h3>Near You</h3>
            <div id="map_element" style = 'min-width:350px; min-height:400px;'></div> 
        </div> 
        <div class="col-md-5">
            <h3 id = 'venue_list_title'> Loading ... </h3>
            <div id="venue_list" style = "max-height:400px; overflow:auto;" > </div> 
        </div>  
    </div> 
</div> 
                


<script>
</script>

