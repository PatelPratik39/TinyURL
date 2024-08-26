import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import React,{useState} from "react";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger
} from "@/components/ui/accordion";

const Landing = () => {
  const [longUrl, setLongUrl] = useState();

  return (
    <div className="flex flex-col items-center">
      <h2 className="my-10 sm:my-16 text-3xl sm:text-6xl lg:text-7xl text-white text-center font-extrabold">
        The only URl Shortner <br /> You&rsquo;ll ever need!!
      </h2>
      <form className="sm:h-14 flex flex-col sm:flex-row w-full md:w-2/4 gap-2">
        <Input
          type="url"
          value={longUrl}
          placeholder="Enter Your Long Url"
          className="h-full flex-1 py-4 px-4"
          onChange={(e) => setLongUrl(e.target.value)}
        />
        <Button className="h-full" type="submit" variants="destructive">
          Shorten!
        </Button>
      </form>
      <img src="/banner.jpg" alt="banner" className="w-full my-11 md:px-11" />
      <Accordion type="multiple" collapsible className="w-full md:px-11">
        <AccordionItem value="item-1">
          <AccordionTrigger>How does the Url Shortner works??</AccordionTrigger>
          <AccordionContent>
            When you enter a long Url, Our System generates a shorter version of
            that URL. This shortened URL redirects to the original long URL when
            accessed.
          </AccordionContent>
        </AccordionItem>
        <AccordionItem value="item-2">
          <AccordionTrigger>
            Do i need an Account to use the app?
          </AccordionTrigger>
          <AccordionContent>
            Yes.Creating an acount allow you to manage your urls, view
            analytics, and customize your short URLs.
          </AccordionContent>
        </AccordionItem>
        <AccordionItem value="item-3">
          <AccordionTrigger>
            What analytics are available for my shortened URLs??
          </AccordionTrigger>
          <AccordionContent>
            You can view the number of clicks, geological data of the clicks and
            device types(mobile/desktop) for each of your shortened URLs.
          </AccordionContent>
        </AccordionItem>
      </Accordion>
    </div>
  );
};

export default Landing;
