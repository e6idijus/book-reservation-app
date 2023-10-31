import { useEffect, useState } from "react";
import Add from "./Add";
import Edit from "./Edit";

export default function Categories() {
  const [categories, setCategories] = useState([]);
  const [addClicked, setAddClicked] = useState(false);
  const [editClicked, setEditClicked] = useState(false);
<<<<<<< HEAD
=======

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
  }, []);
>>>>>>> 9f87f23f0587807c6267c14dae29034b600c7005

  return (
    <div className="container">
      <label htmlFor="category">Current categories:</label>
      <select
        className="form-select"
        name="category"
        defaultValue="default"
      >
        <option
          value="default"
          disabled
          hidden
        >
          Select a category
        </option>

        {categories.map((category, index) => {
          return (
            <option
              key={index}
              value={category.name}
            >
              {category.name}
            </option>
          );
        })}
      </select>
      <button
        className="btn btn-success"
        onClick={() => setAddClicked(true)}
      >
        Add new category
      </button>
      <button
        className="btn btn-info"
        onClick={() => setEditClicked(true)}
      >
        Edit category
      </button>
      {addClicked && <Add />}
      {editClicked && <Edit />}
    </div>
  );
}
