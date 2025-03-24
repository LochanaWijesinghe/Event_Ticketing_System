import React, { useState } from "react";
import axios from "axios";

const Form = () => {
  // State to store user inputs
  const [totalTickets, setTotalTickets] = useState("");
  const [ticketReleaseRate, setTicketsReleaseRate] = useState("");
  const [customerRetrievalRate, setCustomerRetrievalRate] = useState("");
  const [maximumTicketCapacity, setMaximumTicketCapacity] = useState("");

  // Handle form submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const payload = {
      totalTickets: parseInt(totalTickets, 10),
      ticketReleaseRate: parseInt(ticketReleaseRate, 10),
      customerRetrievalRate: parseInt(customerRetrievalRate, 10),
      maximumTicketCapacity: parseInt(maximumTicketCapacity, 10),
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/eventTicketing/configController",
        payload,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        alert("Configuration submitted successfully!");
        console.log("Server Response:", response.data);
      } else {
        alert("Failed to submit configuration.");
      }
    } catch (err) {
      console.error("Error submitting configuration:", err);
      alert("An error occurred while submitting the configuration.");
    }
  };

  return (
    <div className="w-50 border border-2 p-3 mt-3">
      <form className="mt-2 d-flex flex-column gap-2" onSubmit={handleSubmit}>
        <h3>Configuration</h3>

        {/* Total Ticket Count */}
        <div className="row align-items-center">
          <label htmlFor="ticketCount" className="col-form-label">
            Total Ticket Count:
          </label>
          <div className="row-auto w-75">
            <input
              type="number"
              id="ticketCount"
              className="form-control"
              placeholder="Enter the Total Ticket Count"
              value={totalTickets}
              onChange={(e) => setTotalTickets(e.target.value)}
            />
          </div>
        </div>

        {/* Ticket Release Rate */}
        <div className="row align-items-center">
          <label htmlFor="releaseRate" className="col-form-label">
            Ticket Release Rate:
          </label>
          <div className="row-auto w-75">
            <input
              type="number"
              id="releaseRate"
              className="form-control"
              placeholder="Enter the Ticket Release Rate"
              value={ticketReleaseRate}
              onChange={(e) => setTicketsReleaseRate(e.target.value)}
            />
          </div>
        </div>

        {/* Ticket Purchase Rate */}
        <div className="row align-items-center">
          <label htmlFor="purchaseRate" className="col-form-label">
            Ticket Purchase Rate:
          </label>
          <div className="row-auto w-75">
            <input
              type="number"
              id="purchaseRate"
              className="form-control"
              placeholder="Enter the Ticket Purchase Rate"
              value={customerRetrievalRate}
              onChange={(e) => setCustomerRetrievalRate(e.target.value)}
            />
          </div>
        </div>

        {/* Maximum Ticket Capacity */}
        <div className="row align-items-center">
          <label htmlFor="maxCapacity" className="col-form-label">
            Maximum Ticket Capacity:
          </label>
          <div className="row-auto w-75">
            <input
              type="number"
              id="maxCapacity"
              className="form-control"
              placeholder="Enter the Maximum Ticket Capacity"
              value={maximumTicketCapacity}
              onChange={(e) => setMaximumTicketCapacity(e.target.value)}
            />
          </div>
        </div>

        {/* Submit Button */}
        <button type="submit" className="btn btn-primary mt-3">
          Submit Configuration
        </button>
      </form>
    </div>
  );
};

export default Form;
