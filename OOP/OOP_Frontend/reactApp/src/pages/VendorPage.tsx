import "../App.css";
import Form from "../components/Form";
import TopBar from "../components/TopBar";
import Displays from "../components/Displays";

function VendorPage() {
  return (
    <>
      <TopBar />
      <div className="container d-flex gap-3">
        <Form />
        <Displays />
      </div>
    </>
  );
}

export default VendorPage;
