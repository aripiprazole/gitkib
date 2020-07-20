package com.lorenzoog.gitkib.userservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lorenzoog.gitkib.userservice.bodies.ProfileUpdateBody
import com.lorenzoog.gitkib.userservice.config.DefaultUsers
import com.lorenzoog.gitkib.userservice.config.SecurityTestConfig
import com.lorenzoog.gitkib.userservice.controllers.AppProfileController.Companion.INDEX_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.AppProfileController.Companion.SHOW_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.AppProfileController.Companion.UPDATE_ENDPOINT
import com.lorenzoog.gitkib.userservice.entities.Profile
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.ProfileRepository
import com.lorenzoog.gitkib.userservice.utils.mock
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import org.mockito.Mockito.*
import org.springframework.security.test.context.support.WithUserDetails

import org.mockito.Mockito.`when` as every

private val objectMapper = ObjectMapper()

@SpringBootTest(
  classes = [SecurityTestConfig::class]
)
@AutoConfigureMockMvc
// TODO: use a faker library
class AppProfileControllerTests {

  @MockBean
  private lateinit var profileRepository: ProfileRepository

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Test
  fun `test should show profiles paginated when GET ProfileController@index`() {
    val profiles = listOf(
      Profile.mock(User.mock()),
      Profile.mock(User.mock()),
      Profile.mock(User.mock())
    )

    val page: Page<Profile> = PageImpl(
      profiles,
      Pageable.unpaged(),
      profiles.size.toLong()
    )

    every(profileRepository.findAll(any(Pageable::class.java))).thenReturn(page)

    mockMvc.perform(get(INDEX_ENDPOINT).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(page)))

    verify(profileRepository, times(1)).findAll(any(Pageable::class.java))
  }

  @Test
  fun `test should show user that have id 1 when GET ProfileController@show with id path variable 1`() {
    val profile = Profile.mock(User.mock())

    val (id) = profile.user

    every(profileRepository.findByUserId(id)).thenReturn(profile)

    mockMvc.perform(get(SHOW_ENDPOINT.replace("{id}", id.toString())).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(profile)))

    verify(profileRepository, times(1)).findByUserId(id)
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should update profile in database that have the user id 1 and return that in the http response when PUT ProfileController@update with id path variable 1`() {
    val profile = Profile.mock(User.mock())

    val (id) = profile.user

    val body = ProfileUpdateBody(
      name = "fake name",
      company = "fake company",
      discordUsername = "fake discord username",
      twitterUsername = "fake twitter username",
      location = "fake location",
      websiteUrl = "fake website",
      publicEmail = "fake email"
    )

    every(profileRepository.findByUserId(id)).thenReturn(profile)
    every(profileRepository.save(any(Profile::class.java))).thenReturn(profile)

    mockMvc.perform(put(UPDATE_ENDPOINT.replace("{id}", id.toString()))
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(profile)))

    verify(profileRepository, times(1)).findByUserId(id)
    verify(profileRepository, times(1)).save(any(Profile::class.java))
  }

}
