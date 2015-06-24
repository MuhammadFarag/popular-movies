# Popular Movies App (Udacity Android Nanodegree project)


# Project requirements ([source](https://docs.google.com/document/d/1gtXUu1nzLGWrGfVCD6tEA0YHoYA9UNyT2yByqjJemp8/pub?embedded=true))


## How to Use This Guide

This guide will provide you with the project guidelines, directions, and submission instructions for Project 1 and Project 2 if you live in a country that does not support Spotify. Please refer back to this guide until you have successfully submitted your “Popular Movies App”, Stages 1 and 2.

Once you’ve completed the Popular Movies App, you will not have to build the “Spotify Streamer App”, so you will not need to return to those lessons in your Android Developer Nanodegree.

## []()“Popular Movies App” Project Overview

Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing.

You’ll build the complete functionality of this app in two stages which you will submit separately.

Here are the details:

## []()Stage 1 -  Main Discovery Screen, A Details View, and Settings

### []()User Experience

In this stage you’ll build the core experience of your movies app.

You app will:

- Upon launch, present the user with an grid arrangement of movie posters.
- Allow your user to change sort order via a setting:

- The sort order can be by most popular, or by highest-rated

- Allow the user to tap on a movie poster and transition to a details screen with additional information such as:

- original title
- movie poster image thumbnail
- A plot synopsis (called overview in the api)
- user rating (called vote_average in the api)
- release date

### []()Implementation Guidance - Stage 1 

#### []()Setup - Library Configuration 

We recommend that this project use:

1. [Picasso](http://www.google.com/url?q=http%3A%2F%2Fsquare.github.io%2Fpicasso%2F&sa=D&sntz=1&usg=AFQjCNFXCYqJYro4nX9gxkKxRdiTs5BZ_w)* - A powerful library that will handle image loading and caching on your behalf.

*if you prefer, you’re welcome to use an alternate library such as [Glide](https://www.google.com/url?q=https%3A%2F%2Fgithub.com%2Fbumptech%2Fglide&sa=D&sntz=1&usg=AFQjCNF4dQzqKB0Mw8Quveelt9cBcojAmw).

We’ve included this to reduce unnecessary extra work and help you focus on applying your app development skills.

How to Setup Picasso

In your app/build.gradle file, add:

 

repositories {

    mavenCentral()

}

Next, add compile 'com.squareup.picasso:picasso:2.5.2' to your dependencies block.

Using Picasso To Fetch Images and Load Them Into Views

You can use Picasso to easily load album art thumbnails into your views using:

[Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);](http://www.google.com/url?q=http%3A%2F%2Fsquare.github.io%2Fpicasso%2F&sa=D&sntz=1&usg=AFQjCNFXCYqJYro4nX9gxkKxRdiTs5BZ_w)

Picasso will handle loading the images on a background thread, image decompression and caching the images.

#### []()A note on resolving poster paths with themoviedb.org API

You will notice that the API response provides a relative path to a movie poster image when you request the metadata for a specific movie.

For example, the poster path return for Interstellar is “/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg”

You will need to append a base path ahead of this relative path to build the complete url you will need to fetch the image using Picasso.

It’s constructed using 3 parts:

1. The base URL will look like: [http://image.tmdb.org/t/p/](http://www.google.com/url?q=http%3A%2F%2Fimage.tmdb.org%2Ft%2Fp%2F&sa=D&sntz=1&usg=AFQjCNGh1c_grsCerpgiLrflDnqmTo0U1Q).
2. Then you will need a ‘size’, which will be one of the following: "w92", "w154", "w185","w342", "w500", "w780", or "original". For most phones we recommend using “w185”.
3. And finally the poster path returned by the query, in this case “/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg”

Combining these three parts gives us a final url of[http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg](http://www.google.com/url?q=http%3A%2F%2Fimage.tmdb.org%2Ft%2Fp%2Fw185%2F%2FnBNZadXqJSdt05SHLqgT0HuC5Gm.jpg&sa=D&sntz=1&usg=AFQjCNHXxyZWkCbkd3Cp_xFE-8XeWTLckQ) 

 

This is also explained explicitly in the API documentation for [/configuration.](http://www.google.com/url?q=http%3A%2F%2Fdocs.themoviedb.apiary.io%2F%23reference%2Fconfiguration%2Fconfiguration%2Fget%3Fconsole%3D1&sa=D&sntz=1&usg=AFQjCNGAi4SQbRuToHKJAO_F7oS82m09EA)

#### []()Stage 1 - API Hints

1. To fetch popular movies, you will use the API from themoviedb.org.

- If you don’t already have an account, you will need to [create one](https://www.google.com/url?q=https%3A%2F%2Fwww.themoviedb.org%2Faccount%2Fsignup&sa=D&sntz=1&usg=AFQjCNEIHtKFkpI_9l8X_OnJZmEhn08D3g) in order to request an API Key.

- In your request for a key, state that your usage will be foreducational/non-commercial use. You will also need to provide some personal information to complete the request. Once you submit your request, you should receive your key via email shortly after.

- In order to request popular movies you will want to request data from the[/discover/movie endpoint.](http://www.google.com/url?q=http%3A%2F%2Fdocs.themoviedb.apiary.io%2F%23reference%2Fdiscover%2Fdiscovermovie&sa=D&sntz=1&usg=AFQjCNFC4qpIc8ltZVM7BuZkDA28tWiMyg) An API Key is required.
- Once you obtain your key, you append it to your HTTP request as a URL parameter like so:

- http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[YOUR API KEY]

- You will extract the movie id from this request. You will need this in subsequent 