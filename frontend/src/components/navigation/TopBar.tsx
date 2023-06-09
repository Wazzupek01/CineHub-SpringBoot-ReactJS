import classes from "./TopBar.module.css";
import { Link, NavLink } from "react-router-dom";
import SearchModule from "../ui/SearchModule";
import UserMenu from "./UserMenu";
import MenuIcon from '@mui/icons-material/Menu';
import MenuOpenIcon from '@mui/icons-material/MenuOpen';
import { useEffect, useState } from "react";

function TopBar() {
  const [width, setWidth] = useState(window.innerWidth);
  const [menuVisible, setMenuVisible] = useState(true);

  useEffect(() => {
    if(width < 540) setMenuVisible(false);
  },[width])

  useEffect(() => {
    window.addEventListener('resize', () => {
      setWidth(window.innerWidth);
    })
  },[width])


  const nav = (window.innerWidth > 540) ? <nav>
  <ul>
    <li className={classes.border_gradient}>
      <NavLink to="/top/0" className={({ isActive }) =>
          isActive ? classes.active : undefined
        }>
        <div>Top 100</div>
      </NavLink>
    </li>
    <li className={classes.border_gradient}>
      <NavLink to="/browse/0" className={({ isActive }) =>
          isActive ? classes.active : undefined
        }>
        <div>Browse movies</div>
      </NavLink>
    </li>
  </ul>
  <SearchModule />
</nav> : <>
{menuVisible ? <MenuOpenIcon onClick={()=>setMenuVisible(false)}/> : 
<MenuIcon onClick={()=>setMenuVisible(true)}/>}
{(menuVisible) && <nav className={classes.foldable}>
<ul>
    <li className={classes.border_gradient}>
      <NavLink to="/top/0" className={({ isActive }) =>
          isActive ? classes.active : undefined
        }>
        <div>Top 100</div>
      </NavLink>
    </li>
    <li className={classes.border_gradient}>
      <NavLink to="/browse/0" className={({ isActive }) =>
          isActive ? classes.active : undefined
        }>
        <div>Browse movies</div>
      </NavLink>
    </li>
    <SearchModule />
  </ul>
  
</nav>}
</>

  return (
    <div className={classes.topbar}>
      <Link to="/" className={classes.logo}>
        <span className={classes.logo_first_half}>Cine</span>
        <span className={classes.logo_second_half}>HUB</span>
      </Link>
      {nav}
      <UserMenu />
    </div>
  );
}

export default TopBar;
