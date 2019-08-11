package org.sairaa.omowner;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sairaa.omowner.Main.MainContract;
import org.sairaa.omowner.Main.MainPresenter;

import static org.mockito.Mockito.verify;

public class MainActivitySpinnerTest {
    private MainContract.UserActionsListener presenter;

    @Mock
    private MainContract.View mainV;

    @Before
    public void initMock(){
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(mainV);
    }

    @Test
    public void retrieveDetailsOnDay_forParameterToday_setUI_and_disableDateCalled(){
//        presenter.onSpinnerSelection("Today");
//        verify(mainV).disableDateIcon();

    }


}
