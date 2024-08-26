import React, { useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle
} from "@/components/ui/card";
import { Input } from "./ui/input";
import { BarLoader } from "react-spinners";
import { Button } from "./ui/button";
import Error from "./error";
import * as Yup from "Yup";

const Login = () => {
  const [error, setError] = useState([]);
  const [formData, setFormData] = useState({
    email: " ",
    password: " "
  });

  const handleInputChnge = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleLogin = async () => {
    setError([]);
    try {
      const schema = Yup.object().shape({
        email: Yup.string()
          .email("Invalid Email")
          .required("Email is required!"),
        password: Yup.string()
          .min(6, "Password must be 6 characters long")
          .required("Password is required!")
      });
      await schema.validate(formData, { abortEarly: false });
      //   api call
    } catch (error) {
      const newErrors = {};
      e?.inner?.forEach((err) => {
        newErrors[err.path] = err.message;
      });
      setError(newErrors);
    }
  };

  return (
    <>
      <div>
        <Card>
          <CardHeader>
            <CardTitle>Login</CardTitle>
            <CardDescription>
              to your account if you already have one
            </CardDescription>
            <Error message={"Some Error"} />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="space-y-1">
              <Input
                name="email"
                type="email"
                placeholder="Enter Email"
                onChange={handleInputChnge}
              />

              {error.email && <Error message={"some Error"} />}
            </div>
            <div className="space-y-1">
              <Input
                name="password"
                type="password"
                placeholder="Enter Password"
                onChange={handleInputChnge}
              />
              {error.password && <Error message={"Some Error"} />}
            </div>
          </CardContent>
          <CardFooter>
            <Button>
              {true ? <BarLoader size={10} color="#36d7b7" /> : "Login"}{" "}
            </Button>
          </CardFooter>
        </Card>
      </div>
    </>
  );
};

export default Login;
