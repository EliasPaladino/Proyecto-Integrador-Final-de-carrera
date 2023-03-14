import React, { Component, Fragment } from "react";
import PropTypes from "prop-types";
import  {ReactComponent as ReactLogo} from "../../../../assets/icons/map.svg";

class Autocomplete extends Component {
  static propTypes = {
    suggestions: PropTypes.instanceOf(Array),
  };

  static defaultProps = {
    suggestions: []
  };

  handleChanges = () => {
    this.props.onSelect(this.state.userInput);          
  }

  clear = () => {
    this.state.userInput = null         
  }

  constructor(props) {
    super(props);

    this.state = {
      // The active selection's index
      activeSuggestion: 0,
      // The suggestions that match the user's input
      filteredSuggestions: [],
      // Whether or not the suggestion list is shown
      showSuggestions: false,
      // What the user has entered
      userInput: "",
      placeholder: null
    };
  }

  onChange = e => {
    const { suggestions } = this.props;
    const userInput = e.currentTarget.value;

    // Filter our suggestions that don't contain the user's input
    const filteredSuggestions = suggestions.filter(
      suggestion =>{
        if(!userInput) {
          return true;
        } else {
          return suggestion.title.toLowerCase().indexOf(userInput.toLowerCase()) > -1

        }
      }
    );
    this.props.handleChanges(e.currentTarget.value)

    this.setState({
      activeSuggestion: 0,
      filteredSuggestions,
      showSuggestions: true,
      userInput: e.currentTarget.value
    });

  };


  getId = (id) => {
    console.log(id);
  }

  onClick = (e, data) => {
    this.props.handleChanges(data.id)
    this.setState({
      activeSuggestion: 0,
      filteredSuggestions: [],
      showSuggestions: false,
      userInput: data.title
    });

    this.props.selectId(data.id);
  };

  onBlur = e => {
    if(!e.target.value){
      setTimeout(() => {
        this.setState({
        activeSuggestion: 0,
        filteredSuggestions: [],
        showSuggestions: false,
      });
      }, 100);
    }
  };

  onKeyDown = e => {
    const { activeSuggestion, filteredSuggestions } = this.state;
    // User pressed the enter key
    if (e.keyCode === 13) {
      this.setState({
        activeSuggestion: 0,
        showSuggestions: false,
        userInput: filteredSuggestions[activeSuggestion].title
      });
    }
    // User pressed the up arrow
    else if (e.keyCode === 38) {
      if (activeSuggestion === 0) {
        return;
      }

      this.setState({ activeSuggestion: activeSuggestion - 1 });
    }
    // User pressed the down arrow
    else if (e.keyCode === 40) {
      if (activeSuggestion - 1 === filteredSuggestions.length) {
        return;
      }

      this.setState({ activeSuggestion: activeSuggestion + 1 });
    }
  };

  render() {
    const {
      onChange,
      onClick,
      onKeyDown,
      onBlur,
      state: {
        activeSuggestion,
        filteredSuggestions,
        showSuggestions,
        userInput
      }
    } = this;
    let suggestionsListComponent;

    if (showSuggestions) {
      if (filteredSuggestions.length) {
        suggestionsListComponent = (
          <ul className="suggestions">
            {filteredSuggestions.map((suggestion, index) => {
              let className;
              className = "suggestion-li";
              // Flag the active suggestion with a class
              if (index === activeSuggestion) {
                className += " suggestion-active";
              }

              return (
                <li className={className} key={suggestion.title} onClick={(event) => onClick(event, suggestion) }>
                  {suggestion.icono ? <ReactLogo></ReactLogo> : null}
                    <div className="find-items">
                        <label className="suggestion-title"> {suggestion.title}</label>
                        <label className="suggestion-subtitle">{suggestion.subtitle}</label>
                    </div>
                </li>
              );
            })}
          </ul>
        );
      } else {
        suggestionsListComponent = (
          <div className="no-suggestions">
            <em>No existe !</em>
          </div>
        );
      }
    }

    return (
      <Fragment>
        <input
          type="text"
          onChange={onChange}
          onKeyDown={onKeyDown}
          value={userInput}
          placeholder={ this.props.placeholder ? this.props.placeholder :'¿A dónde vamos?'}
          onClick={onChange}
          onBlur={onBlur}
          className={`searcher-inputs ${this.props.placeholder ? "hide-icon" : ""}`}
        />
        {suggestionsListComponent}
      </Fragment>
    );
  }
}

export default Autocomplete;
