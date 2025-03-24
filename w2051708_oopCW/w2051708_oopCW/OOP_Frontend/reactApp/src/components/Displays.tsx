import React, { useState } from "react";
import axios, { AxiosError } from "axios";

const Displays: React.FC = () => {
  const [nov, setNov] = useState<number>(0); // Number of Vendors
  const [noc, setNoc] = useState<number>(0); // Number of Customers

  const inc = (type: string) => {
    if (type === "noc") {
      setNoc((prev) => prev + 1);
    } else if (type === "nov") {
      setNov((prev) => prev + 1);
    }
  };

  const dec = (type: string) => {
    if (type === "noc") {
      setNoc((prev) => Math.max(0, prev - 1));
    } else if (type === "nov") {
      setNov((prev) => Math.max(0, prev - 1));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      // Create an object with the data to send
      const payload = {
        vendorCount: nov,
        customerCount: noc,
      };

      // Sending POST request to the backend
      const response = await axios.post(
        "http://localhost:8080/eventTicketing/vendorAndCustomer",
        payload,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        console.log("Response from server:", response.data);
        alert("Data submitted successfully!");
      } else {
        console.error("Failed to submit data:", response.statusText);
        alert("Error submitting data.");
      }
    } catch (err) {
      const error = err as AxiosError;
      if (error.response && error.response.data) {
        console.error("Error response from server:", error.response.data);
        alert(`Error: ${(error.response.data as any).message || "Server error"}`);
      } else if (error.message) {
        console.error("Error:", error.message);
        alert("An error occurred while submitting data.");
      } else {
        console.error("Unknown error occurred");
        alert("An unknown error occurred while submitting data.");
      }
    }
  };

  const handleStart = async () => {
    try {
      const response = await axios.post("http://localhost:8080/eventTicketing/start");
      if (response.status === 200) {
        alert("Simulation started!");
        console.log("Simulation started", response.data);
      }
    } catch (err) {
      console.error("Error starting simulation", err);
      alert("An error occurred while starting the simulation.");
    }
  };

  const handleStop = async () => {
    try {
      const response = await axios.post("http://localhost:8080/eventTicketing/stop");
      if (response.status === 200) {
        alert("Simulation stopped!");
        console.log("Simulation stopped", response.data);
      }
    } catch (err) {
      console.error("Error stopping simulation", err);
      alert("An error occurred while stopping the simulation.");
    }
  };

  return (
    <div className="w-50 mt-3">
      <div className="border border-2 p-3 d-flex flex-column gap-3">
        <span>
          Number Of Vendors:
          <button className="incDecButton" onClick={() => dec("nov")}>
            -
          </button>{" "}
          {nov}{" "}
          <button className="incDecButton" onClick={() => inc("nov")}>
            +
          </button>
        </span>
        <span>
          Number Of Customers:
          <button className="incDecButton" onClick={() => dec("noc")}>
            -
          </button>{" "}
          {noc}{" "}
          <button className="incDecButton" onClick={() => inc("noc")}>
            +
          </button>
        </span>
        <button className="submitButton" onClick={handleSubmit} style={{ width: "150px" }}>
          Submit
        </button>
      </div>

      <div className="border border-2 p-3 d-flex justify-content-center gap-5 mt-3">
        <button className="btn btn-success" onClick={handleStart}>Start</button>
        <button className="btn btn-danger" onClick={handleStop}>Stop</button>
      </div>
    </div>
  );
};

export default Displays;
