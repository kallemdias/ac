package de.db.ems.adminclient.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.db.ems.adminclient.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BahnhofTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bahnhof.class);
        Bahnhof bahnhof1 = new Bahnhof();
        bahnhof1.setId(1L);
        Bahnhof bahnhof2 = new Bahnhof();
        bahnhof2.setId(bahnhof1.getId());
        assertThat(bahnhof1).isEqualTo(bahnhof2);
        bahnhof2.setId(2L);
        assertThat(bahnhof1).isNotEqualTo(bahnhof2);
        bahnhof1.setId(null);
        assertThat(bahnhof1).isNotEqualTo(bahnhof2);
    }
}
