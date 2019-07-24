<script src="https://unpkg.com/react@16/umd/react.production.min.js"></script>
<script src="https://unpkg.com/react-dom@16/umd/react-dom.production.min.js"></script>

<div id="content" class="container">
    <div style = 'margin-top:-50px;'></div>
    <h3>Video Walkthrough Tutorials</h3>
    <div id="todos-example"></div>
    <br><Br>
    Patient Summary 
    <br>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/Srj7S6hHQPA?rel=0" frameborder="0" allowfullscreen></iframe>
    
    <br><Br>
    Communities 
    <br>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/BYndkOylFWc?rel=0" frameborder="0" allowfullscreen></iframe>
    
    <br><Br>
    Treatments
    <br>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/_RKTknafIk8?rel=0" frameborder="0" allowfullscreen></iframe>

    <br><Br>
    Side Effects
    <br>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/qESY3m_vK3M?rel=0" frameborder="0" allowfullscreen></iframe>

    <br><Br>
    Followup Care
    <br>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/oc0zK3qtbWs?rel=0" frameborder="0" allowfullscreen></iframe>

    <br><Br>
    Preventive Care
    <br>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/YG3M1g6TiM4?rel=0" frameborder="0" allowfullscreen></iframe>

    <br><Br>
    Symptom Management
    <br>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/ACFftQ15_Ew?rel=0" frameborder="0" allowfullscreen></iframe>

    <br><Br>
    Healthy Behavior
    <br>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/UhN0tl_sOdo?rel=0" frameborder="0" allowfullscreen></iframe>

</div>

<script>
    class YoutubeContainer extends React.Component {
        constructor(props) {
            super(props);
            this.state = { items: [], text: '' };
            this.handleChange = this.handleChange.bind(this);
            this.handleSubmit = this.handleSubmit.bind(this);
        }

        render() {
            return React.createElement(
                "div",
                null,
                React.createElement(
                    "h3",
                    null,
                    "TODO"
                ),
                React.createElement(TodoList, { items: this.state.items }),
                React.createElement(
                    "form",
                    { onSubmit: this.handleSubmit },
                    React.createElement(
                        "label",
                        { htmlFor: "new-todo" },
                        "What needs to be done?"
                    ),
                    React.createElement("input", {
                        id: "new-todo",
                        onChange: this.handleChange,
                        value: this.state.text
                    }),
                    React.createElement(
                        "button",
                        null,
                        "Add #",
                        this.state.items.length + 1
                    )
                )
            );
        }

        handleChange(e) {
            this.setState({ text: e.target.value });
        }

        handleSubmit(e) {
            e.preventDefault();
            if (!this.state.text.length) {
                return;
            }
            const newItem = {
                text: this.state.text,
                id: Date.now()
            };
            this.setState(state => ({
                items: state.items.concat(newItem),
                text: ''
            }));
        }
    }

    class VideoWalkthroughList extends React.Component {
        render() {
            return React.createElement(
                "ul",
                null,
                this.props.items.map(item => React.createElement(
                    "li",
                    { key: item.id },
                    item.text
                ))
            );
        }
    }

    ReactDOM.render(React.createElement(TodoApp, null), document.getElementById('todos-example'));
</script>