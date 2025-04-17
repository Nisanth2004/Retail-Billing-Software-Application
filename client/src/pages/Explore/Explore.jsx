import React from 'react'
import './Explore.css'
const Explore = () => {
  return (
   <div className="explore-container text-light">
    <div className="left-column">
      <div className="first-row" style={{overflowY:'auto'}}>
        categories

      </div>
      <hr className="horizontal-line" />
      <div className="second-row" style={{overflowY:'auto'}}>
        items

      </div>

    </div>
    <div className="right-column d-flex flex-column">
      <div className="customer-form-container" style={{height:'15%'}}>
         Customer form
      </div>
      <hr className='my-3 text-light'/>

      <div className="cart-items-container" style={{height:'55%',overflowY:'auto'}} ></div>
      cart items
    </div>

    <div className="card-summary-container"  style={{height:'30%'}}>
      cart summary

    </div>
   </div>
  )
}

export default Explore
