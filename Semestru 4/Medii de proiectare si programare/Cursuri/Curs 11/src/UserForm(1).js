
import React from  'react';
class UserForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {homeTeam: '', awayTeam:'', ticketPrice:'', totalSeats:'', availableSeats:''};

      //  this.handleChange = this.handleChange.bind(this);
       // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleHomeTeamChange=(event) =>{
        this.setState({homeTeam: event.target.value});
    }

    handleAwayTeamChange=(event) =>{
       this.setState({awayTeam: event.target.value});
    }

    handleTicketPriceChange=(event) =>{
        this.setState({ticketPrice: event.target.value});
    }

    handleTotalSeatsChange=(event) =>{
        this.setState({totalSeats: event.target.value});
    }

    handleAvailableSeatsChange=(event) =>{
        this.setState({availableSeats: event.target.value});
    }
    handleSubmit =(event) =>{

        let user={homeTeam:this.state.homeTeam,
            awayTeam:this.state.awayTeam,
            ticketPrice:this.state.ticketPrice,
            totalSeats: this.state.totalSeats,
            availableSeats: this.state.availableSeats
        }
        console.log('A match was submitted: ');
        console.log(user);
        this.props.addFunc(user);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Home Team:
                    <input type="text" value={this.state.homeTeam} onChange={this.handleHomeTeamChange} />
                </label><br/>
                <label>
                    Away Team:
                    <input type="text" value={this.state.awayTeam} onChange={this.handleAwayTeamChange} />
                </label><br/>
                <label>
                    Ticket Price:
                    <input type="text" value={this.state.ticketPrice} onChange={this.handleTicketPriceChange} />
                </label><br/>
                <label>
                    Total Seats:
                    <input type="text" value={this.state.totalSeats} onChange={this.handleTotalSeatsChange} />
                </label><br/>
                <label>
                    Available Seats:
                    <input type="text" value={this.state.availableSeats} onChange={this.handleAvailableSeatsChange} />
                </label><br/>

                <input type="submit" value="Add user" />
            </form>
        );
    }
}
export default UserForm;