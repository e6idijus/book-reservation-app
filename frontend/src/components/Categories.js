import { useEffect, useState } from "react";
import Add from "./Add";
export default function Categories() {
  const [categories, setCategories] = useState([]);
  const [addClicked, setAddClicked] = useState(false);
  useEffect(() => {
    let active = true;
    const fetchData = async () => {
      const response = await fetch("http://localhost:8080/categories");
      const data = await response.json();
      if (active) {
        setCategories(data);
      }
    };
    fetchData();
    return () => {
      active = false;
    };
  }, [categories]);

  return (
<<<<<<< HEAD
    <div className="container">
      <label htmlFor="category">Current categories:</label>
      <select className="form-select" name="category" defaultValue="default">
        <option value="default" disabled hidden>
=======
    <div className="container  col-12 col-sm-8 col-lg-4 mt-3 mb-3">
      <label htmlFor="category ">Current categories:</label>
      <select
        className="form-select mt-3 mb-3"
        name="category mt-3"
        defaultValue="default"
      >
        <option
          value="default"
          disabled
          hidden
        >
>>>>>>> c809c2ce99cf2c20535c7a995851a3f0099466f1
          Select a category
        </option>
        {categories.map((category, index) => {
          return (
            <option key={index} value={category.name}>
              {category.name}
            </option>
          );
        })}
      </select>
<<<<<<< HEAD
      <button className="btn btn-success" onClick={() => setAddClicked(true)}>
=======
      <button
        className="btn btn-success  mb-3"
        onClick={() => setAddClicked(true)}
      >
>>>>>>> c809c2ce99cf2c20535c7a995851a3f0099466f1
        Add new category
      </button>
      {addClicked && <Add setAddClicked={setAddClicked} />}
    </div>
  );
}
