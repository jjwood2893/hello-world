<template>
  <div>
  <b-carousel
    id="carousel-fade"
    style="text-shadow: 0px 0px 2px #000"
    fade
    indicators
    img-width="1024"
    img-height="480"
  >
    <b-carousel-slide
      img-src="@/assets/CarouselSlideOne.jpg"
    ></b-carousel-slide>

    <b-carousel-slide
      img-src="@/assets/CarouselSlideTwo.jpg"
    ></b-carousel-slide>

    <b-carousel-slide
      img-src="@/assets/CarouselSlideThree.jpg"
    ></b-carousel-slide>
  </b-carousel>
</div>


</template>

<script>
export default {
    name: 'carousel-slide',
    data() {
        return {

        }
    }

}
</script>

<style>

</style>