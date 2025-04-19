import React, { useContext, useState } from 'react'
import './Explore.css'
import { AppContext } from '../../context/AppContext'
import DisplayCategory from '../../components/DisplayCategory/DisplayCategory';
import DisplayItems from '../../components/DisplayItems/DisplayItems';
import CustomerForm from '../../components/CustomerForm/CustomerForm';
import CartItems from '../../components/CartItems/CartItems';
import CartSummary from '../../components/CartSummary/CartSummary';
const Explore = () => {

  const {categories}=useContext(AppContext);

  const[selectedCategory,setSelectedCategory]=useState('')
  console.log(categories);
  return (
   <div className="explore-container text-light">
    <div className="left-column">
      <div className="first-row" style={{overflowY:'auto'}}>
        <DisplayCategory 
            categories={categories} 
            selectedCategory={selectedCategory}
            setSelectedCategory={setSelectedCategory}
        />

      </div>
      <hr className="horizontal-line" />
      <div className="second-row" style={{overflowY:'auto'}}>
       <DisplayItems/>

      </div>

    </div>
    <div className="right-column d-flex flex-column">
      <div className="customer-form-container" style={{height:'15%'}}>
         <CustomerForm/>
      </div>
      <hr className='my-3 text-light'/>

      <div className="cart-items-container" style={{height:'55%',overflowY:'auto'}} >

      <CartItems/>
      </div>
      
     

    <div className="cart-summary-container"  style={{height:'30%'}}>
      <CartSummary/>

    </div>
   </div>
   </div>
   
  )
}

export default Explore
