package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.domain.ProjectBusinessgoalId;
import com.qnowapp.domain.ProjectSubgoalId;
import com.qnowapp.domain.ProjectMaingoalId;
import com.qnowapp.domain.ProjectBucketId;
import com.qnowapp.domain.ProjectCostCenterId;
import com.qnowapp.domain.OpportunityPriorityId;
import com.qnowapp.domain.BacklogPractice;
import com.qnowapp.domain.ProjectTheme;
import com.qnowapp.domain.ProjectClass;
import com.qnowapp.domain.ProjectVertical;
import com.qnowapp.domain.ProjectBoardId;
import com.qnowapp.domain.ProjectStatusId;
import com.qnowapp.domain.ProjectTypeId;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.repository.ImProjectsRepository;
import com.qnowapp.service.ImProjectsService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ImProjectsCriteria;
import com.qnowapp.service.ImProjectsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.qnowapp.web.rest.TestUtil.sameInstant;
import static com.qnowapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ImProjectsResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ImProjectsResourceIT {

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NR = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NR = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_TREE_SORTKEY = "AAAAAAAAAA";
    private static final String UPDATED_TREE_SORTKEY = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_CHILD_SORTKEY = "AAAAAAAAAA";
    private static final String UPDATED_MAX_CHILD_SORTKEY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_BILLING_TYPE_ID = 1;
    private static final Integer UPDATED_BILLING_TYPE_ID = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REQUIRES_REPORT_P = false;
    private static final Boolean UPDATED_REQUIRES_REPORT_P = true;

    private static final Double DEFAULT_PROJECT_BUDGET = 1D;
    private static final Double UPDATED_PROJECT_BUDGET = 2D;

    private static final String DEFAULT_PROJECT_RISK = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_RISK = "BBBBBBBBBB";

    private static final String DEFAULT_CORPORATE_SPONSOR = "AAAAAAAAAA";
    private static final String UPDATED_CORPORATE_SPONSOR = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENT_COMPLETED = 1D;
    private static final Double UPDATED_PERCENT_COMPLETED = 2D;

    private static final Double DEFAULT_PROJECT_BUDGET_HOURS = 1D;
    private static final Double UPDATED_PROJECT_BUDGET_HOURS = 2D;

    private static final BigDecimal DEFAULT_COST_QUOTES_CACHE = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_QUOTES_CACHE = new BigDecimal(2);

    private static final Integer DEFAULT_COST_INVOICES_CACHE = 1;
    private static final Integer UPDATED_COST_INVOICES_CACHE = 2;

    private static final Integer DEFAULT_COST_TIMESHEET_PLANNED_CACHE = 1;
    private static final Integer UPDATED_COST_TIMESHEET_PLANNED_CACHE = 2;

    private static final Integer DEFAULT_COST_PURCHASE_ORDERS_CACHE = 1;
    private static final Integer UPDATED_COST_PURCHASE_ORDERS_CACHE = 2;

    private static final Integer DEFAULT_COST_BILLS_CACHE = 1;
    private static final Integer UPDATED_COST_BILLS_CACHE = 2;

    private static final Integer DEFAULT_COST_TIMESHEET_LOGGED_CACHE = 1;
    private static final Integer UPDATED_COST_TIMESHEET_LOGGED_CACHE = 2;

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_TEMPLATE_P = false;
    private static final Boolean UPDATED_TEMPLATE_P = true;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final Double DEFAULT_REPORTED_HOURS_CACHE = 1D;
    private static final Double UPDATED_REPORTED_HOURS_CACHE = 2D;

    private static final Integer DEFAULT_COST_EXPENSE_PLANNED_CACHE = 1;
    private static final Integer UPDATED_COST_EXPENSE_PLANNED_CACHE = 2;

    private static final Integer DEFAULT_COST_EXPENSE_LOGGED_CACHE = 1;
    private static final Integer UPDATED_COST_EXPENSE_LOGGED_CACHE = 2;

    private static final Instant DEFAULT_CONFIRM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONFIRM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_COST_DELIVERY_NOTES_CACHE = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_DELIVERY_NOTES_CACHE = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_COST_CACHE_DIRTY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COST_CACHE_DIRTY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_MILESTONE_P = false;
    private static final Boolean UPDATED_MILESTONE_P = true;

    private static final String DEFAULT_RELEASE_ITEM_P = "AAAAAAAAAA";
    private static final String UPDATED_RELEASE_ITEM_P = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRESALES_PROBABILITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRESALES_PROBABILITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRESALES_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRESALES_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_REPORTED_DAYS_CACHE = new BigDecimal(1);
    private static final BigDecimal UPDATED_REPORTED_DAYS_CACHE = new BigDecimal(2);

    private static final String DEFAULT_PRESALES_VALUE_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_PRESALES_VALUE_CURRENCY = "BBBBBBBBBB";

    private static final Integer DEFAULT_OPPORTUNITY_SALES_STAGE_ID = 1;
    private static final Integer UPDATED_OPPORTUNITY_SALES_STAGE_ID = 2;

    private static final Integer DEFAULT_OPPORTUNITY_CAMPAIGN_ID = 1;
    private static final Integer UPDATED_OPPORTUNITY_CAMPAIGN_ID = 2;

    private static final BigDecimal DEFAULT_SCORE_REVENUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_REVENUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SCORE_STRATEGIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_STRATEGIC = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SCORE_FINANCE_NPV = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_FINANCE_NPV = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SCORE_CUSTOMERS = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_CUSTOMERS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SCORE_FINANCE_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_FINANCE_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COST_BILLS_PLANNED = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_BILLS_PLANNED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COST_EXPENSES_PLANNED = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_EXPENSES_PLANNED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SCORE_RISK = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_RISK = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SCORE_CAPABILITIES = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_CAPABILITIES = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SCORE_EINANCE_ROI = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCORE_EINANCE_ROI = new BigDecimal(2);

    private static final String DEFAULT_PROJECT_USERWISE_BOARD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_USERWISE_BOARD = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROJECT_BRING_NEXTDAY = 1;
    private static final Integer UPDATED_PROJECT_BRING_NEXTDAY = 2;

    private static final String DEFAULT_PROJECT_BRING_SAMEBOARD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_BRING_SAMEBOARD = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NEWBOARD_EVERYTIME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NEWBOARD_EVERYTIME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_USERWISE_BOARD_2 = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_USERWISE_BOARD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_BRING_SAMEBOARD_2 = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_BRING_SAMEBOARD_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME = 1;
    private static final Integer UPDATED_PROJECT_NEWBOARD_2_EVERYTIME = 2;

    private static final String DEFAULT_PROJECT_NEWBOARD_2_ALWAYS = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NEWBOARD_2_ALWAYS = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_REPORT_WEEKLY = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_REPORT_WEEKLY = "BBBBBBBBBB";

    private static final Double DEFAULT_SCORE_GAIN = 1D;
    private static final Double UPDATED_SCORE_GAIN = 2D;

    private static final Double DEFAULT_SCORE_LOSS = 1D;
    private static final Double UPDATED_SCORE_LOSS = 2D;

    private static final Double DEFAULT_SCORE_DELIVERY = 1D;
    private static final Double UPDATED_SCORE_DELIVERY = 2D;

    private static final Double DEFAULT_SCORE_OPERATIONS = 1D;
    private static final Double UPDATED_SCORE_OPERATIONS = 2D;

    private static final Integer DEFAULT_SCORE_WHY = 1;
    private static final Integer UPDATED_SCORE_WHY = 2;

    private static final String DEFAULT_JAVA_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_JAVA_SERVICES = "BBBBBBBBBB";

    private static final String DEFAULT_NET_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_NET_SERVICES = "BBBBBBBBBB";

    private static final String DEFAULT_COLLECTION_LINK = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTION_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_TRAINING_LINK = "AAAAAAAAAA";
    private static final String UPDATED_TRAINING_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_COLLECTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRAINING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRAINING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRAINING_DOC = "AAAAAAAAAA";
    private static final String UPDATED_TRAINING_DOC = "BBBBBBBBBB";

    private static final Integer DEFAULT_TESTING_RICHTEXT = 1;
    private static final Integer UPDATED_TESTING_RICHTEXT = 2;

    private static final String DEFAULT_TEMPLATE_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_CATEGORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_D_TYPE = 1;
    private static final Integer UPDATED_D_TYPE = 2;

    private static final Integer DEFAULT_D_OPTION = 1;
    private static final Integer UPDATED_D_OPTION = 2;

    private static final Integer DEFAULT_D_FILTER = 1;
    private static final Integer UPDATED_D_FILTER = 2;

    private static final Integer DEFAULT_D_ID = 1;
    private static final Integer UPDATED_D_ID = 2;

    private static final Integer DEFAULT_T_TYPE = 1;
    private static final Integer UPDATED_T_TYPE = 2;

    private static final Integer DEFAULT_T_OPTION = 1;
    private static final Integer UPDATED_T_OPTION = 2;

    private static final Integer DEFAULT_T_FILTER = 1;
    private static final Integer UPDATED_T_FILTER = 2;

    private static final Integer DEFAULT_T_ID = 1;
    private static final Integer UPDATED_T_ID = 2;

    private static final String DEFAULT_RISKTYPE = "AAAAAAAAAA";
    private static final String UPDATED_RISKTYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_RISKIMPACT = 1D;
    private static final Double UPDATED_RISKIMPACT = 2D;

    private static final Double DEFAULT_RISKPROBABILITY = 1D;
    private static final Double UPDATED_RISKPROBABILITY = 2D;

    @Autowired
    private ImProjectsRepository imProjectsRepository;

    @Autowired
    private ImProjectsService imProjectsService;

    @Autowired
    private ImProjectsQueryService imProjectsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restImProjectsMockMvc;

    private ImProjects imProjects;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImProjectsResource imProjectsResource = new ImProjectsResource( imProjectsService, imProjectsQueryService);
        this.restImProjectsMockMvc = MockMvcBuilders.standaloneSetup(imProjectsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImProjects createEntity(EntityManager em) {
        ImProjects imProjects = new ImProjects()
            .projectName(DEFAULT_PROJECT_NAME)
            .projectNr(DEFAULT_PROJECT_NR)
            .projectPath(DEFAULT_PROJECT_PATH)
            .treeSortkey(DEFAULT_TREE_SORTKEY)
            .maxChildSortkey(DEFAULT_MAX_CHILD_SORTKEY)
            .description(DEFAULT_DESCRIPTION)
            .billingTypeId(DEFAULT_BILLING_TYPE_ID)
            .note(DEFAULT_NOTE)
            .requiresReportP(DEFAULT_REQUIRES_REPORT_P)
            .projectBudget(DEFAULT_PROJECT_BUDGET)
            .projectRisk(DEFAULT_PROJECT_RISK)
            .corporateSponsor(DEFAULT_CORPORATE_SPONSOR)
            .percentCompleted(DEFAULT_PERCENT_COMPLETED)
            .projectBudgetHours(DEFAULT_PROJECT_BUDGET_HOURS)
            .costQuotesCache(DEFAULT_COST_QUOTES_CACHE)
            .costInvoicesCache(DEFAULT_COST_INVOICES_CACHE)
            .costTimesheetPlannedCache(DEFAULT_COST_TIMESHEET_PLANNED_CACHE)
            .costPurchaseOrdersCache(DEFAULT_COST_PURCHASE_ORDERS_CACHE)
            .costBillsCache(DEFAULT_COST_BILLS_CACHE)
            .costTimesheetLoggedCache(DEFAULT_COST_TIMESHEET_LOGGED_CACHE)
            .endDate(DEFAULT_END_DATE)
            .startDate(DEFAULT_START_DATE)
            .templateP(DEFAULT_TEMPLATE_P)
            .sortOrder(DEFAULT_SORT_ORDER)
            .reportedHoursCache(DEFAULT_REPORTED_HOURS_CACHE)
            .costExpensePlannedCache(DEFAULT_COST_EXPENSE_PLANNED_CACHE)
            .costExpenseLoggedCache(DEFAULT_COST_EXPENSE_LOGGED_CACHE)
            .confirmDate(DEFAULT_CONFIRM_DATE)
            .costDeliveryNotesCache(DEFAULT_COST_DELIVERY_NOTES_CACHE)
            .costCacheDirty(DEFAULT_COST_CACHE_DIRTY)
            .milestoneP(DEFAULT_MILESTONE_P)
            .releaseItemP(DEFAULT_RELEASE_ITEM_P)
            .presalesProbability(DEFAULT_PRESALES_PROBABILITY)
            .presalesValue(DEFAULT_PRESALES_VALUE)
            .reportedDaysCache(DEFAULT_REPORTED_DAYS_CACHE)
            .presalesValueCurrency(DEFAULT_PRESALES_VALUE_CURRENCY)
            .opportunitySalesStageId(DEFAULT_OPPORTUNITY_SALES_STAGE_ID)
            .opportunityCampaignId(DEFAULT_OPPORTUNITY_CAMPAIGN_ID)
            .scoreRevenue(DEFAULT_SCORE_REVENUE)
            .scoreStrategic(DEFAULT_SCORE_STRATEGIC)
            .scoreFinanceNpv(DEFAULT_SCORE_FINANCE_NPV)
            .scoreCustomers(DEFAULT_SCORE_CUSTOMERS)
            .scoreFinanceCost(DEFAULT_SCORE_FINANCE_COST)
            .costBillsPlanned(DEFAULT_COST_BILLS_PLANNED)
            .costExpensesPlanned(DEFAULT_COST_EXPENSES_PLANNED)
            .scoreRisk(DEFAULT_SCORE_RISK)
            .scoreCapabilities(DEFAULT_SCORE_CAPABILITIES)
            .scoreEinanceRoi(DEFAULT_SCORE_EINANCE_ROI)
            .projectUserwiseBoard(DEFAULT_PROJECT_USERWISE_BOARD)
            .projectBringNextday(DEFAULT_PROJECT_BRING_NEXTDAY)
            .projectBringSameboard(DEFAULT_PROJECT_BRING_SAMEBOARD)
            .projectNewboardEverytime(DEFAULT_PROJECT_NEWBOARD_EVERYTIME)
            .projectUserwiseBoard2(DEFAULT_PROJECT_USERWISE_BOARD_2)
            .projectBringSameboard2(DEFAULT_PROJECT_BRING_SAMEBOARD_2)
            .projectNewboard2Everytime(DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME)
            .projectNewboard2Always(DEFAULT_PROJECT_NEWBOARD_2_ALWAYS)
            .projectReportWeekly(DEFAULT_PROJECT_REPORT_WEEKLY)
            .scoreGain(DEFAULT_SCORE_GAIN)
            .scoreLoss(DEFAULT_SCORE_LOSS)
            .scoreDelivery(DEFAULT_SCORE_DELIVERY)
            .scoreOperations(DEFAULT_SCORE_OPERATIONS)
            .scoreWhy(DEFAULT_SCORE_WHY)
            .javaServices(DEFAULT_JAVA_SERVICES)
            .netServices(DEFAULT_NET_SERVICES)
            .collectionLink(DEFAULT_COLLECTION_LINK)
            .trainingLink(DEFAULT_TRAINING_LINK)
            .collectionName(DEFAULT_COLLECTION_NAME)
            .trainingName(DEFAULT_TRAINING_NAME)
            .trainingDoc(DEFAULT_TRAINING_DOC)
            .testingRichtext(DEFAULT_TESTING_RICHTEXT)
            .templateCategory(DEFAULT_TEMPLATE_CATEGORY)
            .dType(DEFAULT_D_TYPE)
            .dOption(DEFAULT_D_OPTION)
            .dFilter(DEFAULT_D_FILTER)
            .dId(DEFAULT_D_ID)
            .tType(DEFAULT_T_TYPE)
            .tOption(DEFAULT_T_OPTION)
            .tFilter(DEFAULT_T_FILTER)
            .tId(DEFAULT_T_ID)
            .risktype(DEFAULT_RISKTYPE)
            .riskimpact(DEFAULT_RISKIMPACT)
            .riskprobability(DEFAULT_RISKPROBABILITY);
        return imProjects;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImProjects createUpdatedEntity(EntityManager em) {
        ImProjects imProjects = new ImProjects()
            .projectName(UPDATED_PROJECT_NAME)
            .projectNr(UPDATED_PROJECT_NR)
            .projectPath(UPDATED_PROJECT_PATH)
            .treeSortkey(UPDATED_TREE_SORTKEY)
            .maxChildSortkey(UPDATED_MAX_CHILD_SORTKEY)
            .description(UPDATED_DESCRIPTION)
            .billingTypeId(UPDATED_BILLING_TYPE_ID)
            .note(UPDATED_NOTE)
            .requiresReportP(UPDATED_REQUIRES_REPORT_P)
            .projectBudget(UPDATED_PROJECT_BUDGET)
            .projectRisk(UPDATED_PROJECT_RISK)
            .corporateSponsor(UPDATED_CORPORATE_SPONSOR)
            .percentCompleted(UPDATED_PERCENT_COMPLETED)
            .projectBudgetHours(UPDATED_PROJECT_BUDGET_HOURS)
            .costQuotesCache(UPDATED_COST_QUOTES_CACHE)
            .costInvoicesCache(UPDATED_COST_INVOICES_CACHE)
            .costTimesheetPlannedCache(UPDATED_COST_TIMESHEET_PLANNED_CACHE)
            .costPurchaseOrdersCache(UPDATED_COST_PURCHASE_ORDERS_CACHE)
            .costBillsCache(UPDATED_COST_BILLS_CACHE)
            .costTimesheetLoggedCache(UPDATED_COST_TIMESHEET_LOGGED_CACHE)
            .endDate(UPDATED_END_DATE)
            .startDate(UPDATED_START_DATE)
            .templateP(UPDATED_TEMPLATE_P)
            .sortOrder(UPDATED_SORT_ORDER)
            .reportedHoursCache(UPDATED_REPORTED_HOURS_CACHE)
            .costExpensePlannedCache(UPDATED_COST_EXPENSE_PLANNED_CACHE)
            .costExpenseLoggedCache(UPDATED_COST_EXPENSE_LOGGED_CACHE)
            .confirmDate(UPDATED_CONFIRM_DATE)
            .costDeliveryNotesCache(UPDATED_COST_DELIVERY_NOTES_CACHE)
            .costCacheDirty(UPDATED_COST_CACHE_DIRTY)
            .milestoneP(UPDATED_MILESTONE_P)
            .releaseItemP(UPDATED_RELEASE_ITEM_P)
            .presalesProbability(UPDATED_PRESALES_PROBABILITY)
            .presalesValue(UPDATED_PRESALES_VALUE)
            .reportedDaysCache(UPDATED_REPORTED_DAYS_CACHE)
            .presalesValueCurrency(UPDATED_PRESALES_VALUE_CURRENCY)
            .opportunitySalesStageId(UPDATED_OPPORTUNITY_SALES_STAGE_ID)
            .opportunityCampaignId(UPDATED_OPPORTUNITY_CAMPAIGN_ID)
            .scoreRevenue(UPDATED_SCORE_REVENUE)
            .scoreStrategic(UPDATED_SCORE_STRATEGIC)
            .scoreFinanceNpv(UPDATED_SCORE_FINANCE_NPV)
            .scoreCustomers(UPDATED_SCORE_CUSTOMERS)
            .scoreFinanceCost(UPDATED_SCORE_FINANCE_COST)
            .costBillsPlanned(UPDATED_COST_BILLS_PLANNED)
            .costExpensesPlanned(UPDATED_COST_EXPENSES_PLANNED)
            .scoreRisk(UPDATED_SCORE_RISK)
            .scoreCapabilities(UPDATED_SCORE_CAPABILITIES)
            .scoreEinanceRoi(UPDATED_SCORE_EINANCE_ROI)
            .projectUserwiseBoard(UPDATED_PROJECT_USERWISE_BOARD)
            .projectBringNextday(UPDATED_PROJECT_BRING_NEXTDAY)
            .projectBringSameboard(UPDATED_PROJECT_BRING_SAMEBOARD)
            .projectNewboardEverytime(UPDATED_PROJECT_NEWBOARD_EVERYTIME)
            .projectUserwiseBoard2(UPDATED_PROJECT_USERWISE_BOARD_2)
            .projectBringSameboard2(UPDATED_PROJECT_BRING_SAMEBOARD_2)
            .projectNewboard2Everytime(UPDATED_PROJECT_NEWBOARD_2_EVERYTIME)
            .projectNewboard2Always(UPDATED_PROJECT_NEWBOARD_2_ALWAYS)
            .projectReportWeekly(UPDATED_PROJECT_REPORT_WEEKLY)
            .scoreGain(UPDATED_SCORE_GAIN)
            .scoreLoss(UPDATED_SCORE_LOSS)
            .scoreDelivery(UPDATED_SCORE_DELIVERY)
            .scoreOperations(UPDATED_SCORE_OPERATIONS)
            .scoreWhy(UPDATED_SCORE_WHY)
            .javaServices(UPDATED_JAVA_SERVICES)
            .netServices(UPDATED_NET_SERVICES)
            .collectionLink(UPDATED_COLLECTION_LINK)
            .trainingLink(UPDATED_TRAINING_LINK)
            .collectionName(UPDATED_COLLECTION_NAME)
            .trainingName(UPDATED_TRAINING_NAME)
            .trainingDoc(UPDATED_TRAINING_DOC)
            .testingRichtext(UPDATED_TESTING_RICHTEXT)
            .templateCategory(UPDATED_TEMPLATE_CATEGORY)
            .dType(UPDATED_D_TYPE)
            .dOption(UPDATED_D_OPTION)
            .dFilter(UPDATED_D_FILTER)
            .dId(UPDATED_D_ID)
            .tType(UPDATED_T_TYPE)
            .tOption(UPDATED_T_OPTION)
            .tFilter(UPDATED_T_FILTER)
            .tId(UPDATED_T_ID)
            .risktype(UPDATED_RISKTYPE)
            .riskimpact(UPDATED_RISKIMPACT)
            .riskprobability(UPDATED_RISKPROBABILITY);
        return imProjects;
    }

    @BeforeEach
    public void initTest() {
        imProjects = createEntity(em);
    }

    @Test
    @Transactional
    public void createImProjects() throws Exception {
        int databaseSizeBeforeCreate = imProjectsRepository.findAll().size();

        // Create the ImProjects
        restImProjectsMockMvc.perform(post("/api/im-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imProjects)))
            .andExpect(status().isCreated());

        // Validate the ImProjects in the database
        List<ImProjects> imProjectsList = imProjectsRepository.findAll();
        assertThat(imProjectsList).hasSize(databaseSizeBeforeCreate + 1);
        ImProjects testImProjects = imProjectsList.get(imProjectsList.size() - 1);
        assertThat(testImProjects.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testImProjects.getProjectNr()).isEqualTo(DEFAULT_PROJECT_NR);
        assertThat(testImProjects.getProjectPath()).isEqualTo(DEFAULT_PROJECT_PATH);
        assertThat(testImProjects.getTreeSortkey()).isEqualTo(DEFAULT_TREE_SORTKEY);
        assertThat(testImProjects.getMaxChildSortkey()).isEqualTo(DEFAULT_MAX_CHILD_SORTKEY);
        assertThat(testImProjects.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testImProjects.getBillingTypeId()).isEqualTo(DEFAULT_BILLING_TYPE_ID);
        assertThat(testImProjects.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testImProjects.isRequiresReportP()).isEqualTo(DEFAULT_REQUIRES_REPORT_P);
        assertThat(testImProjects.getProjectBudget()).isEqualTo(DEFAULT_PROJECT_BUDGET);
        assertThat(testImProjects.getProjectRisk()).isEqualTo(DEFAULT_PROJECT_RISK);
        assertThat(testImProjects.getCorporateSponsor()).isEqualTo(DEFAULT_CORPORATE_SPONSOR);
        assertThat(testImProjects.getPercentCompleted()).isEqualTo(DEFAULT_PERCENT_COMPLETED);
        assertThat(testImProjects.getProjectBudgetHours()).isEqualTo(DEFAULT_PROJECT_BUDGET_HOURS);
        assertThat(testImProjects.getCostQuotesCache()).isEqualTo(DEFAULT_COST_QUOTES_CACHE);
        assertThat(testImProjects.getCostInvoicesCache()).isEqualTo(DEFAULT_COST_INVOICES_CACHE);
        assertThat(testImProjects.getCostTimesheetPlannedCache()).isEqualTo(DEFAULT_COST_TIMESHEET_PLANNED_CACHE);
        assertThat(testImProjects.getCostPurchaseOrdersCache()).isEqualTo(DEFAULT_COST_PURCHASE_ORDERS_CACHE);
        assertThat(testImProjects.getCostBillsCache()).isEqualTo(DEFAULT_COST_BILLS_CACHE);
        assertThat(testImProjects.getCostTimesheetLoggedCache()).isEqualTo(DEFAULT_COST_TIMESHEET_LOGGED_CACHE);
        assertThat(testImProjects.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testImProjects.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testImProjects.isTemplateP()).isEqualTo(DEFAULT_TEMPLATE_P);
        assertThat(testImProjects.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testImProjects.getReportedHoursCache()).isEqualTo(DEFAULT_REPORTED_HOURS_CACHE);
        assertThat(testImProjects.getCostExpensePlannedCache()).isEqualTo(DEFAULT_COST_EXPENSE_PLANNED_CACHE);
        assertThat(testImProjects.getCostExpenseLoggedCache()).isEqualTo(DEFAULT_COST_EXPENSE_LOGGED_CACHE);
        assertThat(testImProjects.getConfirmDate()).isEqualTo(DEFAULT_CONFIRM_DATE);
        assertThat(testImProjects.getCostDeliveryNotesCache()).isEqualTo(DEFAULT_COST_DELIVERY_NOTES_CACHE);
        assertThat(testImProjects.getCostCacheDirty()).isEqualTo(DEFAULT_COST_CACHE_DIRTY);
        assertThat(testImProjects.isMilestoneP()).isEqualTo(DEFAULT_MILESTONE_P);
        assertThat(testImProjects.getReleaseItemP()).isEqualTo(DEFAULT_RELEASE_ITEM_P);
        assertThat(testImProjects.getPresalesProbability()).isEqualTo(DEFAULT_PRESALES_PROBABILITY);
        assertThat(testImProjects.getPresalesValue()).isEqualTo(DEFAULT_PRESALES_VALUE);
        assertThat(testImProjects.getReportedDaysCache()).isEqualTo(DEFAULT_REPORTED_DAYS_CACHE);
        assertThat(testImProjects.getPresalesValueCurrency()).isEqualTo(DEFAULT_PRESALES_VALUE_CURRENCY);
        assertThat(testImProjects.getOpportunitySalesStageId()).isEqualTo(DEFAULT_OPPORTUNITY_SALES_STAGE_ID);
        assertThat(testImProjects.getOpportunityCampaignId()).isEqualTo(DEFAULT_OPPORTUNITY_CAMPAIGN_ID);
        assertThat(testImProjects.getScoreRevenue()).isEqualTo(DEFAULT_SCORE_REVENUE);
        assertThat(testImProjects.getScoreStrategic()).isEqualTo(DEFAULT_SCORE_STRATEGIC);
        assertThat(testImProjects.getScoreFinanceNpv()).isEqualTo(DEFAULT_SCORE_FINANCE_NPV);
        assertThat(testImProjects.getScoreCustomers()).isEqualTo(DEFAULT_SCORE_CUSTOMERS);
        assertThat(testImProjects.getScoreFinanceCost()).isEqualTo(DEFAULT_SCORE_FINANCE_COST);
        assertThat(testImProjects.getCostBillsPlanned()).isEqualTo(DEFAULT_COST_BILLS_PLANNED);
        assertThat(testImProjects.getCostExpensesPlanned()).isEqualTo(DEFAULT_COST_EXPENSES_PLANNED);
        assertThat(testImProjects.getScoreRisk()).isEqualTo(DEFAULT_SCORE_RISK);
        assertThat(testImProjects.getScoreCapabilities()).isEqualTo(DEFAULT_SCORE_CAPABILITIES);
        assertThat(testImProjects.getScoreEinanceRoi()).isEqualTo(DEFAULT_SCORE_EINANCE_ROI);
        assertThat(testImProjects.getProjectUserwiseBoard()).isEqualTo(DEFAULT_PROJECT_USERWISE_BOARD);
        assertThat(testImProjects.getProjectBringNextday()).isEqualTo(DEFAULT_PROJECT_BRING_NEXTDAY);
        assertThat(testImProjects.getProjectBringSameboard()).isEqualTo(DEFAULT_PROJECT_BRING_SAMEBOARD);
        assertThat(testImProjects.getProjectNewboardEverytime()).isEqualTo(DEFAULT_PROJECT_NEWBOARD_EVERYTIME);
        assertThat(testImProjects.getProjectUserwiseBoard2()).isEqualTo(DEFAULT_PROJECT_USERWISE_BOARD_2);
        assertThat(testImProjects.getProjectBringSameboard2()).isEqualTo(DEFAULT_PROJECT_BRING_SAMEBOARD_2);
        assertThat(testImProjects.getProjectNewboard2Everytime()).isEqualTo(DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME);
        assertThat(testImProjects.getProjectNewboard2Always()).isEqualTo(DEFAULT_PROJECT_NEWBOARD_2_ALWAYS);
        assertThat(testImProjects.getProjectReportWeekly()).isEqualTo(DEFAULT_PROJECT_REPORT_WEEKLY);
        assertThat(testImProjects.getScoreGain()).isEqualTo(DEFAULT_SCORE_GAIN);
        assertThat(testImProjects.getScoreLoss()).isEqualTo(DEFAULT_SCORE_LOSS);
        assertThat(testImProjects.getScoreDelivery()).isEqualTo(DEFAULT_SCORE_DELIVERY);
        assertThat(testImProjects.getScoreOperations()).isEqualTo(DEFAULT_SCORE_OPERATIONS);
        assertThat(testImProjects.getScoreWhy()).isEqualTo(DEFAULT_SCORE_WHY);
        assertThat(testImProjects.getJavaServices()).isEqualTo(DEFAULT_JAVA_SERVICES);
        assertThat(testImProjects.getNetServices()).isEqualTo(DEFAULT_NET_SERVICES);
        assertThat(testImProjects.getCollectionLink()).isEqualTo(DEFAULT_COLLECTION_LINK);
        assertThat(testImProjects.getTrainingLink()).isEqualTo(DEFAULT_TRAINING_LINK);
        assertThat(testImProjects.getCollectionName()).isEqualTo(DEFAULT_COLLECTION_NAME);
        assertThat(testImProjects.getTrainingName()).isEqualTo(DEFAULT_TRAINING_NAME);
        assertThat(testImProjects.getTrainingDoc()).isEqualTo(DEFAULT_TRAINING_DOC);
        assertThat(testImProjects.getTestingRichtext()).isEqualTo(DEFAULT_TESTING_RICHTEXT);
        assertThat(testImProjects.getTemplateCategory()).isEqualTo(DEFAULT_TEMPLATE_CATEGORY);
        assertThat(testImProjects.getdType()).isEqualTo(DEFAULT_D_TYPE);
        assertThat(testImProjects.getdOption()).isEqualTo(DEFAULT_D_OPTION);
        assertThat(testImProjects.getdFilter()).isEqualTo(DEFAULT_D_FILTER);
        assertThat(testImProjects.getdId()).isEqualTo(DEFAULT_D_ID);
        assertThat(testImProjects.gettType()).isEqualTo(DEFAULT_T_TYPE);
        assertThat(testImProjects.gettOption()).isEqualTo(DEFAULT_T_OPTION);
        assertThat(testImProjects.gettFilter()).isEqualTo(DEFAULT_T_FILTER);
        assertThat(testImProjects.gettId()).isEqualTo(DEFAULT_T_ID);
        assertThat(testImProjects.getRisktype()).isEqualTo(DEFAULT_RISKTYPE);
        assertThat(testImProjects.getRiskimpact()).isEqualTo(DEFAULT_RISKIMPACT);
        assertThat(testImProjects.getRiskprobability()).isEqualTo(DEFAULT_RISKPROBABILITY);
    }

    @Test
    @Transactional
    public void createImProjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imProjectsRepository.findAll().size();

        // Create the ImProjects with an existing ID
        imProjects.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImProjectsMockMvc.perform(post("/api/im-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imProjects)))
            .andExpect(status().isBadRequest());

        // Validate the ImProjects in the database
        List<ImProjects> imProjectsList = imProjectsRepository.findAll();
        assertThat(imProjectsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = imProjectsRepository.findAll().size();
        // set the field null
        imProjects.setProjectName(null);

        // Create the ImProjects, which fails.

        restImProjectsMockMvc.perform(post("/api/im-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imProjects)))
            .andExpect(status().isBadRequest());

        List<ImProjects> imProjectsList = imProjectsRepository.findAll();
        assertThat(imProjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectNrIsRequired() throws Exception {
        int databaseSizeBeforeTest = imProjectsRepository.findAll().size();
        // set the field null
        imProjects.setProjectNr(null);

        // Create the ImProjects, which fails.

        restImProjectsMockMvc.perform(post("/api/im-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imProjects)))
            .andExpect(status().isBadRequest());

        List<ImProjects> imProjectsList = imProjectsRepository.findAll();
        assertThat(imProjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = imProjectsRepository.findAll().size();
        // set the field null
        imProjects.setProjectPath(null);

        // Create the ImProjects, which fails.

        restImProjectsMockMvc.perform(post("/api/im-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imProjects)))
            .andExpect(status().isBadRequest());

        List<ImProjects> imProjectsList = imProjectsRepository.findAll();
        assertThat(imProjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImProjects() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList
        restImProjectsMockMvc.perform(get("/api/im-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imProjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].projectNr").value(hasItem(DEFAULT_PROJECT_NR.toString())))
            .andExpect(jsonPath("$.[*].projectPath").value(hasItem(DEFAULT_PROJECT_PATH.toString())))
            .andExpect(jsonPath("$.[*].treeSortkey").value(hasItem(DEFAULT_TREE_SORTKEY.toString())))
            .andExpect(jsonPath("$.[*].maxChildSortkey").value(hasItem(DEFAULT_MAX_CHILD_SORTKEY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].billingTypeId").value(hasItem(DEFAULT_BILLING_TYPE_ID)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].requiresReportP").value(hasItem(DEFAULT_REQUIRES_REPORT_P.booleanValue())))
            .andExpect(jsonPath("$.[*].projectBudget").value(hasItem(DEFAULT_PROJECT_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].projectRisk").value(hasItem(DEFAULT_PROJECT_RISK.toString())))
            .andExpect(jsonPath("$.[*].corporateSponsor").value(hasItem(DEFAULT_CORPORATE_SPONSOR.toString())))
            .andExpect(jsonPath("$.[*].percentCompleted").value(hasItem(DEFAULT_PERCENT_COMPLETED.doubleValue())))
            .andExpect(jsonPath("$.[*].projectBudgetHours").value(hasItem(DEFAULT_PROJECT_BUDGET_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].costQuotesCache").value(hasItem(DEFAULT_COST_QUOTES_CACHE.intValue())))
            .andExpect(jsonPath("$.[*].costInvoicesCache").value(hasItem(DEFAULT_COST_INVOICES_CACHE)))
            .andExpect(jsonPath("$.[*].costTimesheetPlannedCache").value(hasItem(DEFAULT_COST_TIMESHEET_PLANNED_CACHE)))
            .andExpect(jsonPath("$.[*].costPurchaseOrdersCache").value(hasItem(DEFAULT_COST_PURCHASE_ORDERS_CACHE)))
            .andExpect(jsonPath("$.[*].costBillsCache").value(hasItem(DEFAULT_COST_BILLS_CACHE)))
            .andExpect(jsonPath("$.[*].costTimesheetLoggedCache").value(hasItem(DEFAULT_COST_TIMESHEET_LOGGED_CACHE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].templateP").value(hasItem(DEFAULT_TEMPLATE_P.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].reportedHoursCache").value(hasItem(DEFAULT_REPORTED_HOURS_CACHE.doubleValue())))
            .andExpect(jsonPath("$.[*].costExpensePlannedCache").value(hasItem(DEFAULT_COST_EXPENSE_PLANNED_CACHE)))
            .andExpect(jsonPath("$.[*].costExpenseLoggedCache").value(hasItem(DEFAULT_COST_EXPENSE_LOGGED_CACHE)))
            .andExpect(jsonPath("$.[*].confirmDate").value(hasItem(DEFAULT_CONFIRM_DATE.toString())))
            .andExpect(jsonPath("$.[*].costDeliveryNotesCache").value(hasItem(DEFAULT_COST_DELIVERY_NOTES_CACHE.intValue())))
            .andExpect(jsonPath("$.[*].costCacheDirty").value(hasItem(sameInstant(DEFAULT_COST_CACHE_DIRTY))))
            .andExpect(jsonPath("$.[*].milestoneP").value(hasItem(DEFAULT_MILESTONE_P.booleanValue())))
            .andExpect(jsonPath("$.[*].releaseItemP").value(hasItem(DEFAULT_RELEASE_ITEM_P.toString())))
            .andExpect(jsonPath("$.[*].presalesProbability").value(hasItem(DEFAULT_PRESALES_PROBABILITY.intValue())))
            .andExpect(jsonPath("$.[*].presalesValue").value(hasItem(DEFAULT_PRESALES_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].reportedDaysCache").value(hasItem(DEFAULT_REPORTED_DAYS_CACHE.intValue())))
            .andExpect(jsonPath("$.[*].presalesValueCurrency").value(hasItem(DEFAULT_PRESALES_VALUE_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].opportunitySalesStageId").value(hasItem(DEFAULT_OPPORTUNITY_SALES_STAGE_ID)))
            .andExpect(jsonPath("$.[*].opportunityCampaignId").value(hasItem(DEFAULT_OPPORTUNITY_CAMPAIGN_ID)))
            .andExpect(jsonPath("$.[*].scoreRevenue").value(hasItem(DEFAULT_SCORE_REVENUE.intValue())))
            .andExpect(jsonPath("$.[*].scoreStrategic").value(hasItem(DEFAULT_SCORE_STRATEGIC.intValue())))
            .andExpect(jsonPath("$.[*].scoreFinanceNpv").value(hasItem(DEFAULT_SCORE_FINANCE_NPV.intValue())))
            .andExpect(jsonPath("$.[*].scoreCustomers").value(hasItem(DEFAULT_SCORE_CUSTOMERS.intValue())))
            .andExpect(jsonPath("$.[*].scoreFinanceCost").value(hasItem(DEFAULT_SCORE_FINANCE_COST.intValue())))
            .andExpect(jsonPath("$.[*].costBillsPlanned").value(hasItem(DEFAULT_COST_BILLS_PLANNED.intValue())))
            .andExpect(jsonPath("$.[*].costExpensesPlanned").value(hasItem(DEFAULT_COST_EXPENSES_PLANNED.intValue())))
            .andExpect(jsonPath("$.[*].scoreRisk").value(hasItem(DEFAULT_SCORE_RISK.intValue())))
            .andExpect(jsonPath("$.[*].scoreCapabilities").value(hasItem(DEFAULT_SCORE_CAPABILITIES.intValue())))
            .andExpect(jsonPath("$.[*].scoreEinanceRoi").value(hasItem(DEFAULT_SCORE_EINANCE_ROI.intValue())))
            .andExpect(jsonPath("$.[*].projectUserwiseBoard").value(hasItem(DEFAULT_PROJECT_USERWISE_BOARD.toString())))
            .andExpect(jsonPath("$.[*].projectBringNextday").value(hasItem(DEFAULT_PROJECT_BRING_NEXTDAY)))
            .andExpect(jsonPath("$.[*].projectBringSameboard").value(hasItem(DEFAULT_PROJECT_BRING_SAMEBOARD.toString())))
            .andExpect(jsonPath("$.[*].projectNewboardEverytime").value(hasItem(DEFAULT_PROJECT_NEWBOARD_EVERYTIME.toString())))
            .andExpect(jsonPath("$.[*].projectUserwiseBoard2").value(hasItem(DEFAULT_PROJECT_USERWISE_BOARD_2.toString())))
            .andExpect(jsonPath("$.[*].projectBringSameboard2").value(hasItem(DEFAULT_PROJECT_BRING_SAMEBOARD_2.toString())))
            .andExpect(jsonPath("$.[*].projectNewboard2Everytime").value(hasItem(DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME)))
            .andExpect(jsonPath("$.[*].projectNewboard2Always").value(hasItem(DEFAULT_PROJECT_NEWBOARD_2_ALWAYS.toString())))
            .andExpect(jsonPath("$.[*].projectReportWeekly").value(hasItem(DEFAULT_PROJECT_REPORT_WEEKLY.toString())))
            .andExpect(jsonPath("$.[*].scoreGain").value(hasItem(DEFAULT_SCORE_GAIN.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreLoss").value(hasItem(DEFAULT_SCORE_LOSS.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreDelivery").value(hasItem(DEFAULT_SCORE_DELIVERY.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreOperations").value(hasItem(DEFAULT_SCORE_OPERATIONS.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreWhy").value(hasItem(DEFAULT_SCORE_WHY)))
            .andExpect(jsonPath("$.[*].javaServices").value(hasItem(DEFAULT_JAVA_SERVICES.toString())))
            .andExpect(jsonPath("$.[*].netServices").value(hasItem(DEFAULT_NET_SERVICES.toString())))
            .andExpect(jsonPath("$.[*].collectionLink").value(hasItem(DEFAULT_COLLECTION_LINK.toString())))
            .andExpect(jsonPath("$.[*].trainingLink").value(hasItem(DEFAULT_TRAINING_LINK.toString())))
            .andExpect(jsonPath("$.[*].collectionName").value(hasItem(DEFAULT_COLLECTION_NAME.toString())))
            .andExpect(jsonPath("$.[*].trainingName").value(hasItem(DEFAULT_TRAINING_NAME.toString())))
            .andExpect(jsonPath("$.[*].trainingDoc").value(hasItem(DEFAULT_TRAINING_DOC.toString())))
            .andExpect(jsonPath("$.[*].testingRichtext").value(hasItem(DEFAULT_TESTING_RICHTEXT)))
            .andExpect(jsonPath("$.[*].templateCategory").value(hasItem(DEFAULT_TEMPLATE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].dType").value(hasItem(DEFAULT_D_TYPE)))
            .andExpect(jsonPath("$.[*].dOption").value(hasItem(DEFAULT_D_OPTION)))
            .andExpect(jsonPath("$.[*].dFilter").value(hasItem(DEFAULT_D_FILTER)))
            .andExpect(jsonPath("$.[*].dId").value(hasItem(DEFAULT_D_ID)))
            .andExpect(jsonPath("$.[*].tType").value(hasItem(DEFAULT_T_TYPE)))
            .andExpect(jsonPath("$.[*].tOption").value(hasItem(DEFAULT_T_OPTION)))
            .andExpect(jsonPath("$.[*].tFilter").value(hasItem(DEFAULT_T_FILTER)))
            .andExpect(jsonPath("$.[*].tId").value(hasItem(DEFAULT_T_ID)))
            .andExpect(jsonPath("$.[*].risktype").value(hasItem(DEFAULT_RISKTYPE.toString())))
            .andExpect(jsonPath("$.[*].riskimpact").value(hasItem(DEFAULT_RISKIMPACT.doubleValue())))
            .andExpect(jsonPath("$.[*].riskprobability").value(hasItem(DEFAULT_RISKPROBABILITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getImProjects() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get the imProjects
        restImProjectsMockMvc.perform(get("/api/im-projects/{id}", imProjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imProjects.getId().intValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.projectNr").value(DEFAULT_PROJECT_NR.toString()))
            .andExpect(jsonPath("$.projectPath").value(DEFAULT_PROJECT_PATH.toString()))
            .andExpect(jsonPath("$.treeSortkey").value(DEFAULT_TREE_SORTKEY.toString()))
            .andExpect(jsonPath("$.maxChildSortkey").value(DEFAULT_MAX_CHILD_SORTKEY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.billingTypeId").value(DEFAULT_BILLING_TYPE_ID))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.requiresReportP").value(DEFAULT_REQUIRES_REPORT_P.booleanValue()))
            .andExpect(jsonPath("$.projectBudget").value(DEFAULT_PROJECT_BUDGET.doubleValue()))
            .andExpect(jsonPath("$.projectRisk").value(DEFAULT_PROJECT_RISK.toString()))
            .andExpect(jsonPath("$.corporateSponsor").value(DEFAULT_CORPORATE_SPONSOR.toString()))
            .andExpect(jsonPath("$.percentCompleted").value(DEFAULT_PERCENT_COMPLETED.doubleValue()))
            .andExpect(jsonPath("$.projectBudgetHours").value(DEFAULT_PROJECT_BUDGET_HOURS.doubleValue()))
            .andExpect(jsonPath("$.costQuotesCache").value(DEFAULT_COST_QUOTES_CACHE.intValue()))
            .andExpect(jsonPath("$.costInvoicesCache").value(DEFAULT_COST_INVOICES_CACHE))
            .andExpect(jsonPath("$.costTimesheetPlannedCache").value(DEFAULT_COST_TIMESHEET_PLANNED_CACHE))
            .andExpect(jsonPath("$.costPurchaseOrdersCache").value(DEFAULT_COST_PURCHASE_ORDERS_CACHE))
            .andExpect(jsonPath("$.costBillsCache").value(DEFAULT_COST_BILLS_CACHE))
            .andExpect(jsonPath("$.costTimesheetLoggedCache").value(DEFAULT_COST_TIMESHEET_LOGGED_CACHE))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.templateP").value(DEFAULT_TEMPLATE_P.booleanValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.reportedHoursCache").value(DEFAULT_REPORTED_HOURS_CACHE.doubleValue()))
            .andExpect(jsonPath("$.costExpensePlannedCache").value(DEFAULT_COST_EXPENSE_PLANNED_CACHE))
            .andExpect(jsonPath("$.costExpenseLoggedCache").value(DEFAULT_COST_EXPENSE_LOGGED_CACHE))
            .andExpect(jsonPath("$.confirmDate").value(DEFAULT_CONFIRM_DATE.toString()))
            .andExpect(jsonPath("$.costDeliveryNotesCache").value(DEFAULT_COST_DELIVERY_NOTES_CACHE.intValue()))
            .andExpect(jsonPath("$.costCacheDirty").value(sameInstant(DEFAULT_COST_CACHE_DIRTY)))
            .andExpect(jsonPath("$.milestoneP").value(DEFAULT_MILESTONE_P.booleanValue()))
            .andExpect(jsonPath("$.releaseItemP").value(DEFAULT_RELEASE_ITEM_P.toString()))
            .andExpect(jsonPath("$.presalesProbability").value(DEFAULT_PRESALES_PROBABILITY.intValue()))
            .andExpect(jsonPath("$.presalesValue").value(DEFAULT_PRESALES_VALUE.intValue()))
            .andExpect(jsonPath("$.reportedDaysCache").value(DEFAULT_REPORTED_DAYS_CACHE.intValue()))
            .andExpect(jsonPath("$.presalesValueCurrency").value(DEFAULT_PRESALES_VALUE_CURRENCY.toString()))
            .andExpect(jsonPath("$.opportunitySalesStageId").value(DEFAULT_OPPORTUNITY_SALES_STAGE_ID))
            .andExpect(jsonPath("$.opportunityCampaignId").value(DEFAULT_OPPORTUNITY_CAMPAIGN_ID))
            .andExpect(jsonPath("$.scoreRevenue").value(DEFAULT_SCORE_REVENUE.intValue()))
            .andExpect(jsonPath("$.scoreStrategic").value(DEFAULT_SCORE_STRATEGIC.intValue()))
            .andExpect(jsonPath("$.scoreFinanceNpv").value(DEFAULT_SCORE_FINANCE_NPV.intValue()))
            .andExpect(jsonPath("$.scoreCustomers").value(DEFAULT_SCORE_CUSTOMERS.intValue()))
            .andExpect(jsonPath("$.scoreFinanceCost").value(DEFAULT_SCORE_FINANCE_COST.intValue()))
            .andExpect(jsonPath("$.costBillsPlanned").value(DEFAULT_COST_BILLS_PLANNED.intValue()))
            .andExpect(jsonPath("$.costExpensesPlanned").value(DEFAULT_COST_EXPENSES_PLANNED.intValue()))
            .andExpect(jsonPath("$.scoreRisk").value(DEFAULT_SCORE_RISK.intValue()))
            .andExpect(jsonPath("$.scoreCapabilities").value(DEFAULT_SCORE_CAPABILITIES.intValue()))
            .andExpect(jsonPath("$.scoreEinanceRoi").value(DEFAULT_SCORE_EINANCE_ROI.intValue()))
            .andExpect(jsonPath("$.projectUserwiseBoard").value(DEFAULT_PROJECT_USERWISE_BOARD.toString()))
            .andExpect(jsonPath("$.projectBringNextday").value(DEFAULT_PROJECT_BRING_NEXTDAY))
            .andExpect(jsonPath("$.projectBringSameboard").value(DEFAULT_PROJECT_BRING_SAMEBOARD.toString()))
            .andExpect(jsonPath("$.projectNewboardEverytime").value(DEFAULT_PROJECT_NEWBOARD_EVERYTIME.toString()))
            .andExpect(jsonPath("$.projectUserwiseBoard2").value(DEFAULT_PROJECT_USERWISE_BOARD_2.toString()))
            .andExpect(jsonPath("$.projectBringSameboard2").value(DEFAULT_PROJECT_BRING_SAMEBOARD_2.toString()))
            .andExpect(jsonPath("$.projectNewboard2Everytime").value(DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME))
            .andExpect(jsonPath("$.projectNewboard2Always").value(DEFAULT_PROJECT_NEWBOARD_2_ALWAYS.toString()))
            .andExpect(jsonPath("$.projectReportWeekly").value(DEFAULT_PROJECT_REPORT_WEEKLY.toString()))
            .andExpect(jsonPath("$.scoreGain").value(DEFAULT_SCORE_GAIN.doubleValue()))
            .andExpect(jsonPath("$.scoreLoss").value(DEFAULT_SCORE_LOSS.doubleValue()))
            .andExpect(jsonPath("$.scoreDelivery").value(DEFAULT_SCORE_DELIVERY.doubleValue()))
            .andExpect(jsonPath("$.scoreOperations").value(DEFAULT_SCORE_OPERATIONS.doubleValue()))
            .andExpect(jsonPath("$.scoreWhy").value(DEFAULT_SCORE_WHY))
            .andExpect(jsonPath("$.javaServices").value(DEFAULT_JAVA_SERVICES.toString()))
            .andExpect(jsonPath("$.netServices").value(DEFAULT_NET_SERVICES.toString()))
            .andExpect(jsonPath("$.collectionLink").value(DEFAULT_COLLECTION_LINK.toString()))
            .andExpect(jsonPath("$.trainingLink").value(DEFAULT_TRAINING_LINK.toString()))
            .andExpect(jsonPath("$.collectionName").value(DEFAULT_COLLECTION_NAME.toString()))
            .andExpect(jsonPath("$.trainingName").value(DEFAULT_TRAINING_NAME.toString()))
            .andExpect(jsonPath("$.trainingDoc").value(DEFAULT_TRAINING_DOC.toString()))
            .andExpect(jsonPath("$.testingRichtext").value(DEFAULT_TESTING_RICHTEXT))
            .andExpect(jsonPath("$.templateCategory").value(DEFAULT_TEMPLATE_CATEGORY.toString()))
            .andExpect(jsonPath("$.dType").value(DEFAULT_D_TYPE))
            .andExpect(jsonPath("$.dOption").value(DEFAULT_D_OPTION))
            .andExpect(jsonPath("$.dFilter").value(DEFAULT_D_FILTER))
            .andExpect(jsonPath("$.dId").value(DEFAULT_D_ID))
            .andExpect(jsonPath("$.tType").value(DEFAULT_T_TYPE))
            .andExpect(jsonPath("$.tOption").value(DEFAULT_T_OPTION))
            .andExpect(jsonPath("$.tFilter").value(DEFAULT_T_FILTER))
            .andExpect(jsonPath("$.tId").value(DEFAULT_T_ID))
            .andExpect(jsonPath("$.risktype").value(DEFAULT_RISKTYPE.toString()))
            .andExpect(jsonPath("$.riskimpact").value(DEFAULT_RISKIMPACT.doubleValue()))
            .andExpect(jsonPath("$.riskprobability").value(DEFAULT_RISKPROBABILITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNameIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectName equals to DEFAULT_PROJECT_NAME
        defaultImProjectsShouldBeFound("projectName.equals=" + DEFAULT_PROJECT_NAME);

        // Get all the imProjectsList where projectName equals to UPDATED_PROJECT_NAME
        defaultImProjectsShouldNotBeFound("projectName.equals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNameIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectName in DEFAULT_PROJECT_NAME or UPDATED_PROJECT_NAME
        defaultImProjectsShouldBeFound("projectName.in=" + DEFAULT_PROJECT_NAME + "," + UPDATED_PROJECT_NAME);

        // Get all the imProjectsList where projectName equals to UPDATED_PROJECT_NAME
        defaultImProjectsShouldNotBeFound("projectName.in=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectName is not null
        defaultImProjectsShouldBeFound("projectName.specified=true");

        // Get all the imProjectsList where projectName is null
        defaultImProjectsShouldNotBeFound("projectName.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNrIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNr equals to DEFAULT_PROJECT_NR
        defaultImProjectsShouldBeFound("projectNr.equals=" + DEFAULT_PROJECT_NR);

        // Get all the imProjectsList where projectNr equals to UPDATED_PROJECT_NR
        defaultImProjectsShouldNotBeFound("projectNr.equals=" + UPDATED_PROJECT_NR);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNrIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNr in DEFAULT_PROJECT_NR or UPDATED_PROJECT_NR
        defaultImProjectsShouldBeFound("projectNr.in=" + DEFAULT_PROJECT_NR + "," + UPDATED_PROJECT_NR);

        // Get all the imProjectsList where projectNr equals to UPDATED_PROJECT_NR
        defaultImProjectsShouldNotBeFound("projectNr.in=" + UPDATED_PROJECT_NR);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNrIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNr is not null
        defaultImProjectsShouldBeFound("projectNr.specified=true");

        // Get all the imProjectsList where projectNr is null
        defaultImProjectsShouldNotBeFound("projectNr.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectPathIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectPath equals to DEFAULT_PROJECT_PATH
        defaultImProjectsShouldBeFound("projectPath.equals=" + DEFAULT_PROJECT_PATH);

        // Get all the imProjectsList where projectPath equals to UPDATED_PROJECT_PATH
        defaultImProjectsShouldNotBeFound("projectPath.equals=" + UPDATED_PROJECT_PATH);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectPathIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectPath in DEFAULT_PROJECT_PATH or UPDATED_PROJECT_PATH
        defaultImProjectsShouldBeFound("projectPath.in=" + DEFAULT_PROJECT_PATH + "," + UPDATED_PROJECT_PATH);

        // Get all the imProjectsList where projectPath equals to UPDATED_PROJECT_PATH
        defaultImProjectsShouldNotBeFound("projectPath.in=" + UPDATED_PROJECT_PATH);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectPath is not null
        defaultImProjectsShouldBeFound("projectPath.specified=true");

        // Get all the imProjectsList where projectPath is null
        defaultImProjectsShouldNotBeFound("projectPath.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByTreeSortkeyIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where treeSortkey equals to DEFAULT_TREE_SORTKEY
        defaultImProjectsShouldBeFound("treeSortkey.equals=" + DEFAULT_TREE_SORTKEY);

        // Get all the imProjectsList where treeSortkey equals to UPDATED_TREE_SORTKEY
        defaultImProjectsShouldNotBeFound("treeSortkey.equals=" + UPDATED_TREE_SORTKEY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTreeSortkeyIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where treeSortkey in DEFAULT_TREE_SORTKEY or UPDATED_TREE_SORTKEY
        defaultImProjectsShouldBeFound("treeSortkey.in=" + DEFAULT_TREE_SORTKEY + "," + UPDATED_TREE_SORTKEY);

        // Get all the imProjectsList where treeSortkey equals to UPDATED_TREE_SORTKEY
        defaultImProjectsShouldNotBeFound("treeSortkey.in=" + UPDATED_TREE_SORTKEY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTreeSortkeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where treeSortkey is not null
        defaultImProjectsShouldBeFound("treeSortkey.specified=true");

        // Get all the imProjectsList where treeSortkey is null
        defaultImProjectsShouldNotBeFound("treeSortkey.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByMaxChildSortkeyIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where maxChildSortkey equals to DEFAULT_MAX_CHILD_SORTKEY
        defaultImProjectsShouldBeFound("maxChildSortkey.equals=" + DEFAULT_MAX_CHILD_SORTKEY);

        // Get all the imProjectsList where maxChildSortkey equals to UPDATED_MAX_CHILD_SORTKEY
        defaultImProjectsShouldNotBeFound("maxChildSortkey.equals=" + UPDATED_MAX_CHILD_SORTKEY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByMaxChildSortkeyIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where maxChildSortkey in DEFAULT_MAX_CHILD_SORTKEY or UPDATED_MAX_CHILD_SORTKEY
        defaultImProjectsShouldBeFound("maxChildSortkey.in=" + DEFAULT_MAX_CHILD_SORTKEY + "," + UPDATED_MAX_CHILD_SORTKEY);

        // Get all the imProjectsList where maxChildSortkey equals to UPDATED_MAX_CHILD_SORTKEY
        defaultImProjectsShouldNotBeFound("maxChildSortkey.in=" + UPDATED_MAX_CHILD_SORTKEY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByMaxChildSortkeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where maxChildSortkey is not null
        defaultImProjectsShouldBeFound("maxChildSortkey.specified=true");

        // Get all the imProjectsList where maxChildSortkey is null
        defaultImProjectsShouldNotBeFound("maxChildSortkey.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where description equals to DEFAULT_DESCRIPTION
        defaultImProjectsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the imProjectsList where description equals to UPDATED_DESCRIPTION
        defaultImProjectsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImProjectsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultImProjectsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the imProjectsList where description equals to UPDATED_DESCRIPTION
        defaultImProjectsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImProjectsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where description is not null
        defaultImProjectsShouldBeFound("description.specified=true");

        // Get all the imProjectsList where description is null
        defaultImProjectsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByBillingTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where billingTypeId equals to DEFAULT_BILLING_TYPE_ID
        defaultImProjectsShouldBeFound("billingTypeId.equals=" + DEFAULT_BILLING_TYPE_ID);

        // Get all the imProjectsList where billingTypeId equals to UPDATED_BILLING_TYPE_ID
        defaultImProjectsShouldNotBeFound("billingTypeId.equals=" + UPDATED_BILLING_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByBillingTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where billingTypeId in DEFAULT_BILLING_TYPE_ID or UPDATED_BILLING_TYPE_ID
        defaultImProjectsShouldBeFound("billingTypeId.in=" + DEFAULT_BILLING_TYPE_ID + "," + UPDATED_BILLING_TYPE_ID);

        // Get all the imProjectsList where billingTypeId equals to UPDATED_BILLING_TYPE_ID
        defaultImProjectsShouldNotBeFound("billingTypeId.in=" + UPDATED_BILLING_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByBillingTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where billingTypeId is not null
        defaultImProjectsShouldBeFound("billingTypeId.specified=true");

        // Get all the imProjectsList where billingTypeId is null
        defaultImProjectsShouldNotBeFound("billingTypeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByBillingTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where billingTypeId greater than or equals to DEFAULT_BILLING_TYPE_ID
        defaultImProjectsShouldBeFound("billingTypeId.greaterOrEqualThan=" + DEFAULT_BILLING_TYPE_ID);

        // Get all the imProjectsList where billingTypeId greater than or equals to UPDATED_BILLING_TYPE_ID
        defaultImProjectsShouldNotBeFound("billingTypeId.greaterOrEqualThan=" + UPDATED_BILLING_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByBillingTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where billingTypeId less than or equals to DEFAULT_BILLING_TYPE_ID
        defaultImProjectsShouldNotBeFound("billingTypeId.lessThan=" + DEFAULT_BILLING_TYPE_ID);

        // Get all the imProjectsList where billingTypeId less than or equals to UPDATED_BILLING_TYPE_ID
        defaultImProjectsShouldBeFound("billingTypeId.lessThan=" + UPDATED_BILLING_TYPE_ID);
    }


    @Test
    @Transactional
    public void getAllImProjectsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where note equals to DEFAULT_NOTE
        defaultImProjectsShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the imProjectsList where note equals to UPDATED_NOTE
        defaultImProjectsShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultImProjectsShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the imProjectsList where note equals to UPDATED_NOTE
        defaultImProjectsShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where note is not null
        defaultImProjectsShouldBeFound("note.specified=true");

        // Get all the imProjectsList where note is null
        defaultImProjectsShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByRequiresReportPIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where requiresReportP equals to DEFAULT_REQUIRES_REPORT_P
        defaultImProjectsShouldBeFound("requiresReportP.equals=" + DEFAULT_REQUIRES_REPORT_P);

        // Get all the imProjectsList where requiresReportP equals to UPDATED_REQUIRES_REPORT_P
        defaultImProjectsShouldNotBeFound("requiresReportP.equals=" + UPDATED_REQUIRES_REPORT_P);
    }

    @Test
    @Transactional
    public void getAllImProjectsByRequiresReportPIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where requiresReportP in DEFAULT_REQUIRES_REPORT_P or UPDATED_REQUIRES_REPORT_P
        defaultImProjectsShouldBeFound("requiresReportP.in=" + DEFAULT_REQUIRES_REPORT_P + "," + UPDATED_REQUIRES_REPORT_P);

        // Get all the imProjectsList where requiresReportP equals to UPDATED_REQUIRES_REPORT_P
        defaultImProjectsShouldNotBeFound("requiresReportP.in=" + UPDATED_REQUIRES_REPORT_P);
    }

    @Test
    @Transactional
    public void getAllImProjectsByRequiresReportPIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where requiresReportP is not null
        defaultImProjectsShouldBeFound("requiresReportP.specified=true");

        // Get all the imProjectsList where requiresReportP is null
        defaultImProjectsShouldNotBeFound("requiresReportP.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBudget equals to DEFAULT_PROJECT_BUDGET
        defaultImProjectsShouldBeFound("projectBudget.equals=" + DEFAULT_PROJECT_BUDGET);

        // Get all the imProjectsList where projectBudget equals to UPDATED_PROJECT_BUDGET
        defaultImProjectsShouldNotBeFound("projectBudget.equals=" + UPDATED_PROJECT_BUDGET);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBudget in DEFAULT_PROJECT_BUDGET or UPDATED_PROJECT_BUDGET
        defaultImProjectsShouldBeFound("projectBudget.in=" + DEFAULT_PROJECT_BUDGET + "," + UPDATED_PROJECT_BUDGET);

        // Get all the imProjectsList where projectBudget equals to UPDATED_PROJECT_BUDGET
        defaultImProjectsShouldNotBeFound("projectBudget.in=" + UPDATED_PROJECT_BUDGET);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBudget is not null
        defaultImProjectsShouldBeFound("projectBudget.specified=true");

        // Get all the imProjectsList where projectBudget is null
        defaultImProjectsShouldNotBeFound("projectBudget.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectRiskIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectRisk equals to DEFAULT_PROJECT_RISK
        defaultImProjectsShouldBeFound("projectRisk.equals=" + DEFAULT_PROJECT_RISK);

        // Get all the imProjectsList where projectRisk equals to UPDATED_PROJECT_RISK
        defaultImProjectsShouldNotBeFound("projectRisk.equals=" + UPDATED_PROJECT_RISK);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectRiskIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectRisk in DEFAULT_PROJECT_RISK or UPDATED_PROJECT_RISK
        defaultImProjectsShouldBeFound("projectRisk.in=" + DEFAULT_PROJECT_RISK + "," + UPDATED_PROJECT_RISK);

        // Get all the imProjectsList where projectRisk equals to UPDATED_PROJECT_RISK
        defaultImProjectsShouldNotBeFound("projectRisk.in=" + UPDATED_PROJECT_RISK);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectRiskIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectRisk is not null
        defaultImProjectsShouldBeFound("projectRisk.specified=true");

        // Get all the imProjectsList where projectRisk is null
        defaultImProjectsShouldNotBeFound("projectRisk.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCorporateSponsorIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where corporateSponsor equals to DEFAULT_CORPORATE_SPONSOR
        defaultImProjectsShouldBeFound("corporateSponsor.equals=" + DEFAULT_CORPORATE_SPONSOR);

        // Get all the imProjectsList where corporateSponsor equals to UPDATED_CORPORATE_SPONSOR
        defaultImProjectsShouldNotBeFound("corporateSponsor.equals=" + UPDATED_CORPORATE_SPONSOR);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCorporateSponsorIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where corporateSponsor in DEFAULT_CORPORATE_SPONSOR or UPDATED_CORPORATE_SPONSOR
        defaultImProjectsShouldBeFound("corporateSponsor.in=" + DEFAULT_CORPORATE_SPONSOR + "," + UPDATED_CORPORATE_SPONSOR);

        // Get all the imProjectsList where corporateSponsor equals to UPDATED_CORPORATE_SPONSOR
        defaultImProjectsShouldNotBeFound("corporateSponsor.in=" + UPDATED_CORPORATE_SPONSOR);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCorporateSponsorIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where corporateSponsor is not null
        defaultImProjectsShouldBeFound("corporateSponsor.specified=true");

        // Get all the imProjectsList where corporateSponsor is null
        defaultImProjectsShouldNotBeFound("corporateSponsor.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByPercentCompletedIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where percentCompleted equals to DEFAULT_PERCENT_COMPLETED
        defaultImProjectsShouldBeFound("percentCompleted.equals=" + DEFAULT_PERCENT_COMPLETED);

        // Get all the imProjectsList where percentCompleted equals to UPDATED_PERCENT_COMPLETED
        defaultImProjectsShouldNotBeFound("percentCompleted.equals=" + UPDATED_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    public void getAllImProjectsByPercentCompletedIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where percentCompleted in DEFAULT_PERCENT_COMPLETED or UPDATED_PERCENT_COMPLETED
        defaultImProjectsShouldBeFound("percentCompleted.in=" + DEFAULT_PERCENT_COMPLETED + "," + UPDATED_PERCENT_COMPLETED);

        // Get all the imProjectsList where percentCompleted equals to UPDATED_PERCENT_COMPLETED
        defaultImProjectsShouldNotBeFound("percentCompleted.in=" + UPDATED_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    public void getAllImProjectsByPercentCompletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where percentCompleted is not null
        defaultImProjectsShouldBeFound("percentCompleted.specified=true");

        // Get all the imProjectsList where percentCompleted is null
        defaultImProjectsShouldNotBeFound("percentCompleted.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBudgetHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBudgetHours equals to DEFAULT_PROJECT_BUDGET_HOURS
        defaultImProjectsShouldBeFound("projectBudgetHours.equals=" + DEFAULT_PROJECT_BUDGET_HOURS);

        // Get all the imProjectsList where projectBudgetHours equals to UPDATED_PROJECT_BUDGET_HOURS
        defaultImProjectsShouldNotBeFound("projectBudgetHours.equals=" + UPDATED_PROJECT_BUDGET_HOURS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBudgetHoursIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBudgetHours in DEFAULT_PROJECT_BUDGET_HOURS or UPDATED_PROJECT_BUDGET_HOURS
        defaultImProjectsShouldBeFound("projectBudgetHours.in=" + DEFAULT_PROJECT_BUDGET_HOURS + "," + UPDATED_PROJECT_BUDGET_HOURS);

        // Get all the imProjectsList where projectBudgetHours equals to UPDATED_PROJECT_BUDGET_HOURS
        defaultImProjectsShouldNotBeFound("projectBudgetHours.in=" + UPDATED_PROJECT_BUDGET_HOURS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBudgetHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBudgetHours is not null
        defaultImProjectsShouldBeFound("projectBudgetHours.specified=true");

        // Get all the imProjectsList where projectBudgetHours is null
        defaultImProjectsShouldNotBeFound("projectBudgetHours.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostQuotesCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costQuotesCache equals to DEFAULT_COST_QUOTES_CACHE
        defaultImProjectsShouldBeFound("costQuotesCache.equals=" + DEFAULT_COST_QUOTES_CACHE);

        // Get all the imProjectsList where costQuotesCache equals to UPDATED_COST_QUOTES_CACHE
        defaultImProjectsShouldNotBeFound("costQuotesCache.equals=" + UPDATED_COST_QUOTES_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostQuotesCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costQuotesCache in DEFAULT_COST_QUOTES_CACHE or UPDATED_COST_QUOTES_CACHE
        defaultImProjectsShouldBeFound("costQuotesCache.in=" + DEFAULT_COST_QUOTES_CACHE + "," + UPDATED_COST_QUOTES_CACHE);

        // Get all the imProjectsList where costQuotesCache equals to UPDATED_COST_QUOTES_CACHE
        defaultImProjectsShouldNotBeFound("costQuotesCache.in=" + UPDATED_COST_QUOTES_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostQuotesCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costQuotesCache is not null
        defaultImProjectsShouldBeFound("costQuotesCache.specified=true");

        // Get all the imProjectsList where costQuotesCache is null
        defaultImProjectsShouldNotBeFound("costQuotesCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostInvoicesCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costInvoicesCache equals to DEFAULT_COST_INVOICES_CACHE
        defaultImProjectsShouldBeFound("costInvoicesCache.equals=" + DEFAULT_COST_INVOICES_CACHE);

        // Get all the imProjectsList where costInvoicesCache equals to UPDATED_COST_INVOICES_CACHE
        defaultImProjectsShouldNotBeFound("costInvoicesCache.equals=" + UPDATED_COST_INVOICES_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostInvoicesCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costInvoicesCache in DEFAULT_COST_INVOICES_CACHE or UPDATED_COST_INVOICES_CACHE
        defaultImProjectsShouldBeFound("costInvoicesCache.in=" + DEFAULT_COST_INVOICES_CACHE + "," + UPDATED_COST_INVOICES_CACHE);

        // Get all the imProjectsList where costInvoicesCache equals to UPDATED_COST_INVOICES_CACHE
        defaultImProjectsShouldNotBeFound("costInvoicesCache.in=" + UPDATED_COST_INVOICES_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostInvoicesCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costInvoicesCache is not null
        defaultImProjectsShouldBeFound("costInvoicesCache.specified=true");

        // Get all the imProjectsList where costInvoicesCache is null
        defaultImProjectsShouldNotBeFound("costInvoicesCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostInvoicesCacheIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costInvoicesCache greater than or equals to DEFAULT_COST_INVOICES_CACHE
        defaultImProjectsShouldBeFound("costInvoicesCache.greaterOrEqualThan=" + DEFAULT_COST_INVOICES_CACHE);

        // Get all the imProjectsList where costInvoicesCache greater than or equals to UPDATED_COST_INVOICES_CACHE
        defaultImProjectsShouldNotBeFound("costInvoicesCache.greaterOrEqualThan=" + UPDATED_COST_INVOICES_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostInvoicesCacheIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costInvoicesCache less than or equals to DEFAULT_COST_INVOICES_CACHE
        defaultImProjectsShouldNotBeFound("costInvoicesCache.lessThan=" + DEFAULT_COST_INVOICES_CACHE);

        // Get all the imProjectsList where costInvoicesCache less than or equals to UPDATED_COST_INVOICES_CACHE
        defaultImProjectsShouldBeFound("costInvoicesCache.lessThan=" + UPDATED_COST_INVOICES_CACHE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetPlannedCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetPlannedCache equals to DEFAULT_COST_TIMESHEET_PLANNED_CACHE
        defaultImProjectsShouldBeFound("costTimesheetPlannedCache.equals=" + DEFAULT_COST_TIMESHEET_PLANNED_CACHE);

        // Get all the imProjectsList where costTimesheetPlannedCache equals to UPDATED_COST_TIMESHEET_PLANNED_CACHE
        defaultImProjectsShouldNotBeFound("costTimesheetPlannedCache.equals=" + UPDATED_COST_TIMESHEET_PLANNED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetPlannedCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetPlannedCache in DEFAULT_COST_TIMESHEET_PLANNED_CACHE or UPDATED_COST_TIMESHEET_PLANNED_CACHE
        defaultImProjectsShouldBeFound("costTimesheetPlannedCache.in=" + DEFAULT_COST_TIMESHEET_PLANNED_CACHE + "," + UPDATED_COST_TIMESHEET_PLANNED_CACHE);

        // Get all the imProjectsList where costTimesheetPlannedCache equals to UPDATED_COST_TIMESHEET_PLANNED_CACHE
        defaultImProjectsShouldNotBeFound("costTimesheetPlannedCache.in=" + UPDATED_COST_TIMESHEET_PLANNED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetPlannedCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetPlannedCache is not null
        defaultImProjectsShouldBeFound("costTimesheetPlannedCache.specified=true");

        // Get all the imProjectsList where costTimesheetPlannedCache is null
        defaultImProjectsShouldNotBeFound("costTimesheetPlannedCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetPlannedCacheIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetPlannedCache greater than or equals to DEFAULT_COST_TIMESHEET_PLANNED_CACHE
        defaultImProjectsShouldBeFound("costTimesheetPlannedCache.greaterOrEqualThan=" + DEFAULT_COST_TIMESHEET_PLANNED_CACHE);

        // Get all the imProjectsList where costTimesheetPlannedCache greater than or equals to UPDATED_COST_TIMESHEET_PLANNED_CACHE
        defaultImProjectsShouldNotBeFound("costTimesheetPlannedCache.greaterOrEqualThan=" + UPDATED_COST_TIMESHEET_PLANNED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetPlannedCacheIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetPlannedCache less than or equals to DEFAULT_COST_TIMESHEET_PLANNED_CACHE
        defaultImProjectsShouldNotBeFound("costTimesheetPlannedCache.lessThan=" + DEFAULT_COST_TIMESHEET_PLANNED_CACHE);

        // Get all the imProjectsList where costTimesheetPlannedCache less than or equals to UPDATED_COST_TIMESHEET_PLANNED_CACHE
        defaultImProjectsShouldBeFound("costTimesheetPlannedCache.lessThan=" + UPDATED_COST_TIMESHEET_PLANNED_CACHE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByCostPurchaseOrdersCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costPurchaseOrdersCache equals to DEFAULT_COST_PURCHASE_ORDERS_CACHE
        defaultImProjectsShouldBeFound("costPurchaseOrdersCache.equals=" + DEFAULT_COST_PURCHASE_ORDERS_CACHE);

        // Get all the imProjectsList where costPurchaseOrdersCache equals to UPDATED_COST_PURCHASE_ORDERS_CACHE
        defaultImProjectsShouldNotBeFound("costPurchaseOrdersCache.equals=" + UPDATED_COST_PURCHASE_ORDERS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostPurchaseOrdersCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costPurchaseOrdersCache in DEFAULT_COST_PURCHASE_ORDERS_CACHE or UPDATED_COST_PURCHASE_ORDERS_CACHE
        defaultImProjectsShouldBeFound("costPurchaseOrdersCache.in=" + DEFAULT_COST_PURCHASE_ORDERS_CACHE + "," + UPDATED_COST_PURCHASE_ORDERS_CACHE);

        // Get all the imProjectsList where costPurchaseOrdersCache equals to UPDATED_COST_PURCHASE_ORDERS_CACHE
        defaultImProjectsShouldNotBeFound("costPurchaseOrdersCache.in=" + UPDATED_COST_PURCHASE_ORDERS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostPurchaseOrdersCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costPurchaseOrdersCache is not null
        defaultImProjectsShouldBeFound("costPurchaseOrdersCache.specified=true");

        // Get all the imProjectsList where costPurchaseOrdersCache is null
        defaultImProjectsShouldNotBeFound("costPurchaseOrdersCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostPurchaseOrdersCacheIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costPurchaseOrdersCache greater than or equals to DEFAULT_COST_PURCHASE_ORDERS_CACHE
        defaultImProjectsShouldBeFound("costPurchaseOrdersCache.greaterOrEqualThan=" + DEFAULT_COST_PURCHASE_ORDERS_CACHE);

        // Get all the imProjectsList where costPurchaseOrdersCache greater than or equals to UPDATED_COST_PURCHASE_ORDERS_CACHE
        defaultImProjectsShouldNotBeFound("costPurchaseOrdersCache.greaterOrEqualThan=" + UPDATED_COST_PURCHASE_ORDERS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostPurchaseOrdersCacheIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costPurchaseOrdersCache less than or equals to DEFAULT_COST_PURCHASE_ORDERS_CACHE
        defaultImProjectsShouldNotBeFound("costPurchaseOrdersCache.lessThan=" + DEFAULT_COST_PURCHASE_ORDERS_CACHE);

        // Get all the imProjectsList where costPurchaseOrdersCache less than or equals to UPDATED_COST_PURCHASE_ORDERS_CACHE
        defaultImProjectsShouldBeFound("costPurchaseOrdersCache.lessThan=" + UPDATED_COST_PURCHASE_ORDERS_CACHE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByCostBillsCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costBillsCache equals to DEFAULT_COST_BILLS_CACHE
        defaultImProjectsShouldBeFound("costBillsCache.equals=" + DEFAULT_COST_BILLS_CACHE);

        // Get all the imProjectsList where costBillsCache equals to UPDATED_COST_BILLS_CACHE
        defaultImProjectsShouldNotBeFound("costBillsCache.equals=" + UPDATED_COST_BILLS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostBillsCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costBillsCache in DEFAULT_COST_BILLS_CACHE or UPDATED_COST_BILLS_CACHE
        defaultImProjectsShouldBeFound("costBillsCache.in=" + DEFAULT_COST_BILLS_CACHE + "," + UPDATED_COST_BILLS_CACHE);

        // Get all the imProjectsList where costBillsCache equals to UPDATED_COST_BILLS_CACHE
        defaultImProjectsShouldNotBeFound("costBillsCache.in=" + UPDATED_COST_BILLS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostBillsCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costBillsCache is not null
        defaultImProjectsShouldBeFound("costBillsCache.specified=true");

        // Get all the imProjectsList where costBillsCache is null
        defaultImProjectsShouldNotBeFound("costBillsCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostBillsCacheIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costBillsCache greater than or equals to DEFAULT_COST_BILLS_CACHE
        defaultImProjectsShouldBeFound("costBillsCache.greaterOrEqualThan=" + DEFAULT_COST_BILLS_CACHE);

        // Get all the imProjectsList where costBillsCache greater than or equals to UPDATED_COST_BILLS_CACHE
        defaultImProjectsShouldNotBeFound("costBillsCache.greaterOrEqualThan=" + UPDATED_COST_BILLS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostBillsCacheIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costBillsCache less than or equals to DEFAULT_COST_BILLS_CACHE
        defaultImProjectsShouldNotBeFound("costBillsCache.lessThan=" + DEFAULT_COST_BILLS_CACHE);

        // Get all the imProjectsList where costBillsCache less than or equals to UPDATED_COST_BILLS_CACHE
        defaultImProjectsShouldBeFound("costBillsCache.lessThan=" + UPDATED_COST_BILLS_CACHE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetLoggedCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetLoggedCache equals to DEFAULT_COST_TIMESHEET_LOGGED_CACHE
        defaultImProjectsShouldBeFound("costTimesheetLoggedCache.equals=" + DEFAULT_COST_TIMESHEET_LOGGED_CACHE);

        // Get all the imProjectsList where costTimesheetLoggedCache equals to UPDATED_COST_TIMESHEET_LOGGED_CACHE
        defaultImProjectsShouldNotBeFound("costTimesheetLoggedCache.equals=" + UPDATED_COST_TIMESHEET_LOGGED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetLoggedCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetLoggedCache in DEFAULT_COST_TIMESHEET_LOGGED_CACHE or UPDATED_COST_TIMESHEET_LOGGED_CACHE
        defaultImProjectsShouldBeFound("costTimesheetLoggedCache.in=" + DEFAULT_COST_TIMESHEET_LOGGED_CACHE + "," + UPDATED_COST_TIMESHEET_LOGGED_CACHE);

        // Get all the imProjectsList where costTimesheetLoggedCache equals to UPDATED_COST_TIMESHEET_LOGGED_CACHE
        defaultImProjectsShouldNotBeFound("costTimesheetLoggedCache.in=" + UPDATED_COST_TIMESHEET_LOGGED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetLoggedCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetLoggedCache is not null
        defaultImProjectsShouldBeFound("costTimesheetLoggedCache.specified=true");

        // Get all the imProjectsList where costTimesheetLoggedCache is null
        defaultImProjectsShouldNotBeFound("costTimesheetLoggedCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetLoggedCacheIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetLoggedCache greater than or equals to DEFAULT_COST_TIMESHEET_LOGGED_CACHE
        defaultImProjectsShouldBeFound("costTimesheetLoggedCache.greaterOrEqualThan=" + DEFAULT_COST_TIMESHEET_LOGGED_CACHE);

        // Get all the imProjectsList where costTimesheetLoggedCache greater than or equals to UPDATED_COST_TIMESHEET_LOGGED_CACHE
        defaultImProjectsShouldNotBeFound("costTimesheetLoggedCache.greaterOrEqualThan=" + UPDATED_COST_TIMESHEET_LOGGED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostTimesheetLoggedCacheIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costTimesheetLoggedCache less than or equals to DEFAULT_COST_TIMESHEET_LOGGED_CACHE
        defaultImProjectsShouldNotBeFound("costTimesheetLoggedCache.lessThan=" + DEFAULT_COST_TIMESHEET_LOGGED_CACHE);

        // Get all the imProjectsList where costTimesheetLoggedCache less than or equals to UPDATED_COST_TIMESHEET_LOGGED_CACHE
        defaultImProjectsShouldBeFound("costTimesheetLoggedCache.lessThan=" + UPDATED_COST_TIMESHEET_LOGGED_CACHE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where endDate equals to DEFAULT_END_DATE
        defaultImProjectsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the imProjectsList where endDate equals to UPDATED_END_DATE
        defaultImProjectsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultImProjectsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the imProjectsList where endDate equals to UPDATED_END_DATE
        defaultImProjectsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where endDate is not null
        defaultImProjectsShouldBeFound("endDate.specified=true");

        // Get all the imProjectsList where endDate is null
        defaultImProjectsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where endDate greater than or equals to DEFAULT_END_DATE
        defaultImProjectsShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the imProjectsList where endDate greater than or equals to UPDATED_END_DATE
        defaultImProjectsShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where endDate less than or equals to DEFAULT_END_DATE
        defaultImProjectsShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the imProjectsList where endDate less than or equals to UPDATED_END_DATE
        defaultImProjectsShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where startDate equals to DEFAULT_START_DATE
        defaultImProjectsShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the imProjectsList where startDate equals to UPDATED_START_DATE
        defaultImProjectsShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultImProjectsShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the imProjectsList where startDate equals to UPDATED_START_DATE
        defaultImProjectsShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where startDate is not null
        defaultImProjectsShouldBeFound("startDate.specified=true");

        // Get all the imProjectsList where startDate is null
        defaultImProjectsShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where startDate greater than or equals to DEFAULT_START_DATE
        defaultImProjectsShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the imProjectsList where startDate greater than or equals to UPDATED_START_DATE
        defaultImProjectsShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where startDate less than or equals to DEFAULT_START_DATE
        defaultImProjectsShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the imProjectsList where startDate less than or equals to UPDATED_START_DATE
        defaultImProjectsShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByTemplatePIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where templateP equals to DEFAULT_TEMPLATE_P
        defaultImProjectsShouldBeFound("templateP.equals=" + DEFAULT_TEMPLATE_P);

        // Get all the imProjectsList where templateP equals to UPDATED_TEMPLATE_P
        defaultImProjectsShouldNotBeFound("templateP.equals=" + UPDATED_TEMPLATE_P);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTemplatePIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where templateP in DEFAULT_TEMPLATE_P or UPDATED_TEMPLATE_P
        defaultImProjectsShouldBeFound("templateP.in=" + DEFAULT_TEMPLATE_P + "," + UPDATED_TEMPLATE_P);

        // Get all the imProjectsList where templateP equals to UPDATED_TEMPLATE_P
        defaultImProjectsShouldNotBeFound("templateP.in=" + UPDATED_TEMPLATE_P);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTemplatePIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where templateP is not null
        defaultImProjectsShouldBeFound("templateP.specified=true");

        // Get all the imProjectsList where templateP is null
        defaultImProjectsShouldNotBeFound("templateP.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultImProjectsShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the imProjectsList where sortOrder equals to UPDATED_SORT_ORDER
        defaultImProjectsShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultImProjectsShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the imProjectsList where sortOrder equals to UPDATED_SORT_ORDER
        defaultImProjectsShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where sortOrder is not null
        defaultImProjectsShouldBeFound("sortOrder.specified=true");

        // Get all the imProjectsList where sortOrder is null
        defaultImProjectsShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where sortOrder greater than or equals to DEFAULT_SORT_ORDER
        defaultImProjectsShouldBeFound("sortOrder.greaterOrEqualThan=" + DEFAULT_SORT_ORDER);

        // Get all the imProjectsList where sortOrder greater than or equals to UPDATED_SORT_ORDER
        defaultImProjectsShouldNotBeFound("sortOrder.greaterOrEqualThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where sortOrder less than or equals to DEFAULT_SORT_ORDER
        defaultImProjectsShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the imProjectsList where sortOrder less than or equals to UPDATED_SORT_ORDER
        defaultImProjectsShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllImProjectsByReportedHoursCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where reportedHoursCache equals to DEFAULT_REPORTED_HOURS_CACHE
        defaultImProjectsShouldBeFound("reportedHoursCache.equals=" + DEFAULT_REPORTED_HOURS_CACHE);

        // Get all the imProjectsList where reportedHoursCache equals to UPDATED_REPORTED_HOURS_CACHE
        defaultImProjectsShouldNotBeFound("reportedHoursCache.equals=" + UPDATED_REPORTED_HOURS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByReportedHoursCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where reportedHoursCache in DEFAULT_REPORTED_HOURS_CACHE or UPDATED_REPORTED_HOURS_CACHE
        defaultImProjectsShouldBeFound("reportedHoursCache.in=" + DEFAULT_REPORTED_HOURS_CACHE + "," + UPDATED_REPORTED_HOURS_CACHE);

        // Get all the imProjectsList where reportedHoursCache equals to UPDATED_REPORTED_HOURS_CACHE
        defaultImProjectsShouldNotBeFound("reportedHoursCache.in=" + UPDATED_REPORTED_HOURS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByReportedHoursCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where reportedHoursCache is not null
        defaultImProjectsShouldBeFound("reportedHoursCache.specified=true");

        // Get all the imProjectsList where reportedHoursCache is null
        defaultImProjectsShouldNotBeFound("reportedHoursCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpensePlannedCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpensePlannedCache equals to DEFAULT_COST_EXPENSE_PLANNED_CACHE
        defaultImProjectsShouldBeFound("costExpensePlannedCache.equals=" + DEFAULT_COST_EXPENSE_PLANNED_CACHE);

        // Get all the imProjectsList where costExpensePlannedCache equals to UPDATED_COST_EXPENSE_PLANNED_CACHE
        defaultImProjectsShouldNotBeFound("costExpensePlannedCache.equals=" + UPDATED_COST_EXPENSE_PLANNED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpensePlannedCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpensePlannedCache in DEFAULT_COST_EXPENSE_PLANNED_CACHE or UPDATED_COST_EXPENSE_PLANNED_CACHE
        defaultImProjectsShouldBeFound("costExpensePlannedCache.in=" + DEFAULT_COST_EXPENSE_PLANNED_CACHE + "," + UPDATED_COST_EXPENSE_PLANNED_CACHE);

        // Get all the imProjectsList where costExpensePlannedCache equals to UPDATED_COST_EXPENSE_PLANNED_CACHE
        defaultImProjectsShouldNotBeFound("costExpensePlannedCache.in=" + UPDATED_COST_EXPENSE_PLANNED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpensePlannedCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpensePlannedCache is not null
        defaultImProjectsShouldBeFound("costExpensePlannedCache.specified=true");

        // Get all the imProjectsList where costExpensePlannedCache is null
        defaultImProjectsShouldNotBeFound("costExpensePlannedCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpensePlannedCacheIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpensePlannedCache greater than or equals to DEFAULT_COST_EXPENSE_PLANNED_CACHE
        defaultImProjectsShouldBeFound("costExpensePlannedCache.greaterOrEqualThan=" + DEFAULT_COST_EXPENSE_PLANNED_CACHE);

        // Get all the imProjectsList where costExpensePlannedCache greater than or equals to UPDATED_COST_EXPENSE_PLANNED_CACHE
        defaultImProjectsShouldNotBeFound("costExpensePlannedCache.greaterOrEqualThan=" + UPDATED_COST_EXPENSE_PLANNED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpensePlannedCacheIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpensePlannedCache less than or equals to DEFAULT_COST_EXPENSE_PLANNED_CACHE
        defaultImProjectsShouldNotBeFound("costExpensePlannedCache.lessThan=" + DEFAULT_COST_EXPENSE_PLANNED_CACHE);

        // Get all the imProjectsList where costExpensePlannedCache less than or equals to UPDATED_COST_EXPENSE_PLANNED_CACHE
        defaultImProjectsShouldBeFound("costExpensePlannedCache.lessThan=" + UPDATED_COST_EXPENSE_PLANNED_CACHE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByCostExpenseLoggedCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpenseLoggedCache equals to DEFAULT_COST_EXPENSE_LOGGED_CACHE
        defaultImProjectsShouldBeFound("costExpenseLoggedCache.equals=" + DEFAULT_COST_EXPENSE_LOGGED_CACHE);

        // Get all the imProjectsList where costExpenseLoggedCache equals to UPDATED_COST_EXPENSE_LOGGED_CACHE
        defaultImProjectsShouldNotBeFound("costExpenseLoggedCache.equals=" + UPDATED_COST_EXPENSE_LOGGED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpenseLoggedCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpenseLoggedCache in DEFAULT_COST_EXPENSE_LOGGED_CACHE or UPDATED_COST_EXPENSE_LOGGED_CACHE
        defaultImProjectsShouldBeFound("costExpenseLoggedCache.in=" + DEFAULT_COST_EXPENSE_LOGGED_CACHE + "," + UPDATED_COST_EXPENSE_LOGGED_CACHE);

        // Get all the imProjectsList where costExpenseLoggedCache equals to UPDATED_COST_EXPENSE_LOGGED_CACHE
        defaultImProjectsShouldNotBeFound("costExpenseLoggedCache.in=" + UPDATED_COST_EXPENSE_LOGGED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpenseLoggedCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpenseLoggedCache is not null
        defaultImProjectsShouldBeFound("costExpenseLoggedCache.specified=true");

        // Get all the imProjectsList where costExpenseLoggedCache is null
        defaultImProjectsShouldNotBeFound("costExpenseLoggedCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpenseLoggedCacheIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpenseLoggedCache greater than or equals to DEFAULT_COST_EXPENSE_LOGGED_CACHE
        defaultImProjectsShouldBeFound("costExpenseLoggedCache.greaterOrEqualThan=" + DEFAULT_COST_EXPENSE_LOGGED_CACHE);

        // Get all the imProjectsList where costExpenseLoggedCache greater than or equals to UPDATED_COST_EXPENSE_LOGGED_CACHE
        defaultImProjectsShouldNotBeFound("costExpenseLoggedCache.greaterOrEqualThan=" + UPDATED_COST_EXPENSE_LOGGED_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpenseLoggedCacheIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpenseLoggedCache less than or equals to DEFAULT_COST_EXPENSE_LOGGED_CACHE
        defaultImProjectsShouldNotBeFound("costExpenseLoggedCache.lessThan=" + DEFAULT_COST_EXPENSE_LOGGED_CACHE);

        // Get all the imProjectsList where costExpenseLoggedCache less than or equals to UPDATED_COST_EXPENSE_LOGGED_CACHE
        defaultImProjectsShouldBeFound("costExpenseLoggedCache.lessThan=" + UPDATED_COST_EXPENSE_LOGGED_CACHE);
    }


    @Test
    @Transactional
    public void getAllImProjectsByConfirmDateIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where confirmDate equals to DEFAULT_CONFIRM_DATE
        defaultImProjectsShouldBeFound("confirmDate.equals=" + DEFAULT_CONFIRM_DATE);

        // Get all the imProjectsList where confirmDate equals to UPDATED_CONFIRM_DATE
        defaultImProjectsShouldNotBeFound("confirmDate.equals=" + UPDATED_CONFIRM_DATE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByConfirmDateIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where confirmDate in DEFAULT_CONFIRM_DATE or UPDATED_CONFIRM_DATE
        defaultImProjectsShouldBeFound("confirmDate.in=" + DEFAULT_CONFIRM_DATE + "," + UPDATED_CONFIRM_DATE);

        // Get all the imProjectsList where confirmDate equals to UPDATED_CONFIRM_DATE
        defaultImProjectsShouldNotBeFound("confirmDate.in=" + UPDATED_CONFIRM_DATE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByConfirmDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where confirmDate is not null
        defaultImProjectsShouldBeFound("confirmDate.specified=true");

        // Get all the imProjectsList where confirmDate is null
        defaultImProjectsShouldNotBeFound("confirmDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostDeliveryNotesCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costDeliveryNotesCache equals to DEFAULT_COST_DELIVERY_NOTES_CACHE
        defaultImProjectsShouldBeFound("costDeliveryNotesCache.equals=" + DEFAULT_COST_DELIVERY_NOTES_CACHE);

        // Get all the imProjectsList where costDeliveryNotesCache equals to UPDATED_COST_DELIVERY_NOTES_CACHE
        defaultImProjectsShouldNotBeFound("costDeliveryNotesCache.equals=" + UPDATED_COST_DELIVERY_NOTES_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostDeliveryNotesCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costDeliveryNotesCache in DEFAULT_COST_DELIVERY_NOTES_CACHE or UPDATED_COST_DELIVERY_NOTES_CACHE
        defaultImProjectsShouldBeFound("costDeliveryNotesCache.in=" + DEFAULT_COST_DELIVERY_NOTES_CACHE + "," + UPDATED_COST_DELIVERY_NOTES_CACHE);

        // Get all the imProjectsList where costDeliveryNotesCache equals to UPDATED_COST_DELIVERY_NOTES_CACHE
        defaultImProjectsShouldNotBeFound("costDeliveryNotesCache.in=" + UPDATED_COST_DELIVERY_NOTES_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostDeliveryNotesCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costDeliveryNotesCache is not null
        defaultImProjectsShouldBeFound("costDeliveryNotesCache.specified=true");

        // Get all the imProjectsList where costDeliveryNotesCache is null
        defaultImProjectsShouldNotBeFound("costDeliveryNotesCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostCacheDirtyIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costCacheDirty equals to DEFAULT_COST_CACHE_DIRTY
        defaultImProjectsShouldBeFound("costCacheDirty.equals=" + DEFAULT_COST_CACHE_DIRTY);

        // Get all the imProjectsList where costCacheDirty equals to UPDATED_COST_CACHE_DIRTY
        defaultImProjectsShouldNotBeFound("costCacheDirty.equals=" + UPDATED_COST_CACHE_DIRTY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostCacheDirtyIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costCacheDirty in DEFAULT_COST_CACHE_DIRTY or UPDATED_COST_CACHE_DIRTY
        defaultImProjectsShouldBeFound("costCacheDirty.in=" + DEFAULT_COST_CACHE_DIRTY + "," + UPDATED_COST_CACHE_DIRTY);

        // Get all the imProjectsList where costCacheDirty equals to UPDATED_COST_CACHE_DIRTY
        defaultImProjectsShouldNotBeFound("costCacheDirty.in=" + UPDATED_COST_CACHE_DIRTY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostCacheDirtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costCacheDirty is not null
        defaultImProjectsShouldBeFound("costCacheDirty.specified=true");

        // Get all the imProjectsList where costCacheDirty is null
        defaultImProjectsShouldNotBeFound("costCacheDirty.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostCacheDirtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costCacheDirty greater than or equals to DEFAULT_COST_CACHE_DIRTY
        defaultImProjectsShouldBeFound("costCacheDirty.greaterOrEqualThan=" + DEFAULT_COST_CACHE_DIRTY);

        // Get all the imProjectsList where costCacheDirty greater than or equals to UPDATED_COST_CACHE_DIRTY
        defaultImProjectsShouldNotBeFound("costCacheDirty.greaterOrEqualThan=" + UPDATED_COST_CACHE_DIRTY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostCacheDirtyIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costCacheDirty less than or equals to DEFAULT_COST_CACHE_DIRTY
        defaultImProjectsShouldNotBeFound("costCacheDirty.lessThan=" + DEFAULT_COST_CACHE_DIRTY);

        // Get all the imProjectsList where costCacheDirty less than or equals to UPDATED_COST_CACHE_DIRTY
        defaultImProjectsShouldBeFound("costCacheDirty.lessThan=" + UPDATED_COST_CACHE_DIRTY);
    }


    @Test
    @Transactional
    public void getAllImProjectsByMilestonePIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where milestoneP equals to DEFAULT_MILESTONE_P
        defaultImProjectsShouldBeFound("milestoneP.equals=" + DEFAULT_MILESTONE_P);

        // Get all the imProjectsList where milestoneP equals to UPDATED_MILESTONE_P
        defaultImProjectsShouldNotBeFound("milestoneP.equals=" + UPDATED_MILESTONE_P);
    }

    @Test
    @Transactional
    public void getAllImProjectsByMilestonePIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where milestoneP in DEFAULT_MILESTONE_P or UPDATED_MILESTONE_P
        defaultImProjectsShouldBeFound("milestoneP.in=" + DEFAULT_MILESTONE_P + "," + UPDATED_MILESTONE_P);

        // Get all the imProjectsList where milestoneP equals to UPDATED_MILESTONE_P
        defaultImProjectsShouldNotBeFound("milestoneP.in=" + UPDATED_MILESTONE_P);
    }

    @Test
    @Transactional
    public void getAllImProjectsByMilestonePIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where milestoneP is not null
        defaultImProjectsShouldBeFound("milestoneP.specified=true");

        // Get all the imProjectsList where milestoneP is null
        defaultImProjectsShouldNotBeFound("milestoneP.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByReleaseItemPIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where releaseItemP equals to DEFAULT_RELEASE_ITEM_P
        defaultImProjectsShouldBeFound("releaseItemP.equals=" + DEFAULT_RELEASE_ITEM_P);

        // Get all the imProjectsList where releaseItemP equals to UPDATED_RELEASE_ITEM_P
        defaultImProjectsShouldNotBeFound("releaseItemP.equals=" + UPDATED_RELEASE_ITEM_P);
    }

    @Test
    @Transactional
    public void getAllImProjectsByReleaseItemPIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where releaseItemP in DEFAULT_RELEASE_ITEM_P or UPDATED_RELEASE_ITEM_P
        defaultImProjectsShouldBeFound("releaseItemP.in=" + DEFAULT_RELEASE_ITEM_P + "," + UPDATED_RELEASE_ITEM_P);

        // Get all the imProjectsList where releaseItemP equals to UPDATED_RELEASE_ITEM_P
        defaultImProjectsShouldNotBeFound("releaseItemP.in=" + UPDATED_RELEASE_ITEM_P);
    }

    @Test
    @Transactional
    public void getAllImProjectsByReleaseItemPIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where releaseItemP is not null
        defaultImProjectsShouldBeFound("releaseItemP.specified=true");

        // Get all the imProjectsList where releaseItemP is null
        defaultImProjectsShouldNotBeFound("releaseItemP.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesProbabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesProbability equals to DEFAULT_PRESALES_PROBABILITY
        defaultImProjectsShouldBeFound("presalesProbability.equals=" + DEFAULT_PRESALES_PROBABILITY);

        // Get all the imProjectsList where presalesProbability equals to UPDATED_PRESALES_PROBABILITY
        defaultImProjectsShouldNotBeFound("presalesProbability.equals=" + UPDATED_PRESALES_PROBABILITY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesProbabilityIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesProbability in DEFAULT_PRESALES_PROBABILITY or UPDATED_PRESALES_PROBABILITY
        defaultImProjectsShouldBeFound("presalesProbability.in=" + DEFAULT_PRESALES_PROBABILITY + "," + UPDATED_PRESALES_PROBABILITY);

        // Get all the imProjectsList where presalesProbability equals to UPDATED_PRESALES_PROBABILITY
        defaultImProjectsShouldNotBeFound("presalesProbability.in=" + UPDATED_PRESALES_PROBABILITY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesProbabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesProbability is not null
        defaultImProjectsShouldBeFound("presalesProbability.specified=true");

        // Get all the imProjectsList where presalesProbability is null
        defaultImProjectsShouldNotBeFound("presalesProbability.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesValueIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesValue equals to DEFAULT_PRESALES_VALUE
        defaultImProjectsShouldBeFound("presalesValue.equals=" + DEFAULT_PRESALES_VALUE);

        // Get all the imProjectsList where presalesValue equals to UPDATED_PRESALES_VALUE
        defaultImProjectsShouldNotBeFound("presalesValue.equals=" + UPDATED_PRESALES_VALUE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesValueIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesValue in DEFAULT_PRESALES_VALUE or UPDATED_PRESALES_VALUE
        defaultImProjectsShouldBeFound("presalesValue.in=" + DEFAULT_PRESALES_VALUE + "," + UPDATED_PRESALES_VALUE);

        // Get all the imProjectsList where presalesValue equals to UPDATED_PRESALES_VALUE
        defaultImProjectsShouldNotBeFound("presalesValue.in=" + UPDATED_PRESALES_VALUE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesValue is not null
        defaultImProjectsShouldBeFound("presalesValue.specified=true");

        // Get all the imProjectsList where presalesValue is null
        defaultImProjectsShouldNotBeFound("presalesValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByReportedDaysCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where reportedDaysCache equals to DEFAULT_REPORTED_DAYS_CACHE
        defaultImProjectsShouldBeFound("reportedDaysCache.equals=" + DEFAULT_REPORTED_DAYS_CACHE);

        // Get all the imProjectsList where reportedDaysCache equals to UPDATED_REPORTED_DAYS_CACHE
        defaultImProjectsShouldNotBeFound("reportedDaysCache.equals=" + UPDATED_REPORTED_DAYS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByReportedDaysCacheIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where reportedDaysCache in DEFAULT_REPORTED_DAYS_CACHE or UPDATED_REPORTED_DAYS_CACHE
        defaultImProjectsShouldBeFound("reportedDaysCache.in=" + DEFAULT_REPORTED_DAYS_CACHE + "," + UPDATED_REPORTED_DAYS_CACHE);

        // Get all the imProjectsList where reportedDaysCache equals to UPDATED_REPORTED_DAYS_CACHE
        defaultImProjectsShouldNotBeFound("reportedDaysCache.in=" + UPDATED_REPORTED_DAYS_CACHE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByReportedDaysCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where reportedDaysCache is not null
        defaultImProjectsShouldBeFound("reportedDaysCache.specified=true");

        // Get all the imProjectsList where reportedDaysCache is null
        defaultImProjectsShouldNotBeFound("reportedDaysCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesValueCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesValueCurrency equals to DEFAULT_PRESALES_VALUE_CURRENCY
        defaultImProjectsShouldBeFound("presalesValueCurrency.equals=" + DEFAULT_PRESALES_VALUE_CURRENCY);

        // Get all the imProjectsList where presalesValueCurrency equals to UPDATED_PRESALES_VALUE_CURRENCY
        defaultImProjectsShouldNotBeFound("presalesValueCurrency.equals=" + UPDATED_PRESALES_VALUE_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesValueCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesValueCurrency in DEFAULT_PRESALES_VALUE_CURRENCY or UPDATED_PRESALES_VALUE_CURRENCY
        defaultImProjectsShouldBeFound("presalesValueCurrency.in=" + DEFAULT_PRESALES_VALUE_CURRENCY + "," + UPDATED_PRESALES_VALUE_CURRENCY);

        // Get all the imProjectsList where presalesValueCurrency equals to UPDATED_PRESALES_VALUE_CURRENCY
        defaultImProjectsShouldNotBeFound("presalesValueCurrency.in=" + UPDATED_PRESALES_VALUE_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByPresalesValueCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where presalesValueCurrency is not null
        defaultImProjectsShouldBeFound("presalesValueCurrency.specified=true");

        // Get all the imProjectsList where presalesValueCurrency is null
        defaultImProjectsShouldNotBeFound("presalesValueCurrency.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunitySalesStageIdIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunitySalesStageId equals to DEFAULT_OPPORTUNITY_SALES_STAGE_ID
        defaultImProjectsShouldBeFound("opportunitySalesStageId.equals=" + DEFAULT_OPPORTUNITY_SALES_STAGE_ID);

        // Get all the imProjectsList where opportunitySalesStageId equals to UPDATED_OPPORTUNITY_SALES_STAGE_ID
        defaultImProjectsShouldNotBeFound("opportunitySalesStageId.equals=" + UPDATED_OPPORTUNITY_SALES_STAGE_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunitySalesStageIdIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunitySalesStageId in DEFAULT_OPPORTUNITY_SALES_STAGE_ID or UPDATED_OPPORTUNITY_SALES_STAGE_ID
        defaultImProjectsShouldBeFound("opportunitySalesStageId.in=" + DEFAULT_OPPORTUNITY_SALES_STAGE_ID + "," + UPDATED_OPPORTUNITY_SALES_STAGE_ID);

        // Get all the imProjectsList where opportunitySalesStageId equals to UPDATED_OPPORTUNITY_SALES_STAGE_ID
        defaultImProjectsShouldNotBeFound("opportunitySalesStageId.in=" + UPDATED_OPPORTUNITY_SALES_STAGE_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunitySalesStageIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunitySalesStageId is not null
        defaultImProjectsShouldBeFound("opportunitySalesStageId.specified=true");

        // Get all the imProjectsList where opportunitySalesStageId is null
        defaultImProjectsShouldNotBeFound("opportunitySalesStageId.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunitySalesStageIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunitySalesStageId greater than or equals to DEFAULT_OPPORTUNITY_SALES_STAGE_ID
        defaultImProjectsShouldBeFound("opportunitySalesStageId.greaterOrEqualThan=" + DEFAULT_OPPORTUNITY_SALES_STAGE_ID);

        // Get all the imProjectsList where opportunitySalesStageId greater than or equals to UPDATED_OPPORTUNITY_SALES_STAGE_ID
        defaultImProjectsShouldNotBeFound("opportunitySalesStageId.greaterOrEqualThan=" + UPDATED_OPPORTUNITY_SALES_STAGE_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunitySalesStageIdIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunitySalesStageId less than or equals to DEFAULT_OPPORTUNITY_SALES_STAGE_ID
        defaultImProjectsShouldNotBeFound("opportunitySalesStageId.lessThan=" + DEFAULT_OPPORTUNITY_SALES_STAGE_ID);

        // Get all the imProjectsList where opportunitySalesStageId less than or equals to UPDATED_OPPORTUNITY_SALES_STAGE_ID
        defaultImProjectsShouldBeFound("opportunitySalesStageId.lessThan=" + UPDATED_OPPORTUNITY_SALES_STAGE_ID);
    }


    @Test
    @Transactional
    public void getAllImProjectsByOpportunityCampaignIdIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunityCampaignId equals to DEFAULT_OPPORTUNITY_CAMPAIGN_ID
        defaultImProjectsShouldBeFound("opportunityCampaignId.equals=" + DEFAULT_OPPORTUNITY_CAMPAIGN_ID);

        // Get all the imProjectsList where opportunityCampaignId equals to UPDATED_OPPORTUNITY_CAMPAIGN_ID
        defaultImProjectsShouldNotBeFound("opportunityCampaignId.equals=" + UPDATED_OPPORTUNITY_CAMPAIGN_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunityCampaignIdIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunityCampaignId in DEFAULT_OPPORTUNITY_CAMPAIGN_ID or UPDATED_OPPORTUNITY_CAMPAIGN_ID
        defaultImProjectsShouldBeFound("opportunityCampaignId.in=" + DEFAULT_OPPORTUNITY_CAMPAIGN_ID + "," + UPDATED_OPPORTUNITY_CAMPAIGN_ID);

        // Get all the imProjectsList where opportunityCampaignId equals to UPDATED_OPPORTUNITY_CAMPAIGN_ID
        defaultImProjectsShouldNotBeFound("opportunityCampaignId.in=" + UPDATED_OPPORTUNITY_CAMPAIGN_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunityCampaignIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunityCampaignId is not null
        defaultImProjectsShouldBeFound("opportunityCampaignId.specified=true");

        // Get all the imProjectsList where opportunityCampaignId is null
        defaultImProjectsShouldNotBeFound("opportunityCampaignId.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunityCampaignIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunityCampaignId greater than or equals to DEFAULT_OPPORTUNITY_CAMPAIGN_ID
        defaultImProjectsShouldBeFound("opportunityCampaignId.greaterOrEqualThan=" + DEFAULT_OPPORTUNITY_CAMPAIGN_ID);

        // Get all the imProjectsList where opportunityCampaignId greater than or equals to UPDATED_OPPORTUNITY_CAMPAIGN_ID
        defaultImProjectsShouldNotBeFound("opportunityCampaignId.greaterOrEqualThan=" + UPDATED_OPPORTUNITY_CAMPAIGN_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsByOpportunityCampaignIdIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where opportunityCampaignId less than or equals to DEFAULT_OPPORTUNITY_CAMPAIGN_ID
        defaultImProjectsShouldNotBeFound("opportunityCampaignId.lessThan=" + DEFAULT_OPPORTUNITY_CAMPAIGN_ID);

        // Get all the imProjectsList where opportunityCampaignId less than or equals to UPDATED_OPPORTUNITY_CAMPAIGN_ID
        defaultImProjectsShouldBeFound("opportunityCampaignId.lessThan=" + UPDATED_OPPORTUNITY_CAMPAIGN_ID);
    }


    @Test
    @Transactional
    public void getAllImProjectsByScoreRevenueIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreRevenue equals to DEFAULT_SCORE_REVENUE
        defaultImProjectsShouldBeFound("scoreRevenue.equals=" + DEFAULT_SCORE_REVENUE);

        // Get all the imProjectsList where scoreRevenue equals to UPDATED_SCORE_REVENUE
        defaultImProjectsShouldNotBeFound("scoreRevenue.equals=" + UPDATED_SCORE_REVENUE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreRevenueIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreRevenue in DEFAULT_SCORE_REVENUE or UPDATED_SCORE_REVENUE
        defaultImProjectsShouldBeFound("scoreRevenue.in=" + DEFAULT_SCORE_REVENUE + "," + UPDATED_SCORE_REVENUE);

        // Get all the imProjectsList where scoreRevenue equals to UPDATED_SCORE_REVENUE
        defaultImProjectsShouldNotBeFound("scoreRevenue.in=" + UPDATED_SCORE_REVENUE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreRevenueIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreRevenue is not null
        defaultImProjectsShouldBeFound("scoreRevenue.specified=true");

        // Get all the imProjectsList where scoreRevenue is null
        defaultImProjectsShouldNotBeFound("scoreRevenue.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreStrategicIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreStrategic equals to DEFAULT_SCORE_STRATEGIC
        defaultImProjectsShouldBeFound("scoreStrategic.equals=" + DEFAULT_SCORE_STRATEGIC);

        // Get all the imProjectsList where scoreStrategic equals to UPDATED_SCORE_STRATEGIC
        defaultImProjectsShouldNotBeFound("scoreStrategic.equals=" + UPDATED_SCORE_STRATEGIC);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreStrategicIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreStrategic in DEFAULT_SCORE_STRATEGIC or UPDATED_SCORE_STRATEGIC
        defaultImProjectsShouldBeFound("scoreStrategic.in=" + DEFAULT_SCORE_STRATEGIC + "," + UPDATED_SCORE_STRATEGIC);

        // Get all the imProjectsList where scoreStrategic equals to UPDATED_SCORE_STRATEGIC
        defaultImProjectsShouldNotBeFound("scoreStrategic.in=" + UPDATED_SCORE_STRATEGIC);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreStrategicIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreStrategic is not null
        defaultImProjectsShouldBeFound("scoreStrategic.specified=true");

        // Get all the imProjectsList where scoreStrategic is null
        defaultImProjectsShouldNotBeFound("scoreStrategic.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreFinanceNpvIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreFinanceNpv equals to DEFAULT_SCORE_FINANCE_NPV
        defaultImProjectsShouldBeFound("scoreFinanceNpv.equals=" + DEFAULT_SCORE_FINANCE_NPV);

        // Get all the imProjectsList where scoreFinanceNpv equals to UPDATED_SCORE_FINANCE_NPV
        defaultImProjectsShouldNotBeFound("scoreFinanceNpv.equals=" + UPDATED_SCORE_FINANCE_NPV);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreFinanceNpvIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreFinanceNpv in DEFAULT_SCORE_FINANCE_NPV or UPDATED_SCORE_FINANCE_NPV
        defaultImProjectsShouldBeFound("scoreFinanceNpv.in=" + DEFAULT_SCORE_FINANCE_NPV + "," + UPDATED_SCORE_FINANCE_NPV);

        // Get all the imProjectsList where scoreFinanceNpv equals to UPDATED_SCORE_FINANCE_NPV
        defaultImProjectsShouldNotBeFound("scoreFinanceNpv.in=" + UPDATED_SCORE_FINANCE_NPV);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreFinanceNpvIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreFinanceNpv is not null
        defaultImProjectsShouldBeFound("scoreFinanceNpv.specified=true");

        // Get all the imProjectsList where scoreFinanceNpv is null
        defaultImProjectsShouldNotBeFound("scoreFinanceNpv.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreCustomers equals to DEFAULT_SCORE_CUSTOMERS
        defaultImProjectsShouldBeFound("scoreCustomers.equals=" + DEFAULT_SCORE_CUSTOMERS);

        // Get all the imProjectsList where scoreCustomers equals to UPDATED_SCORE_CUSTOMERS
        defaultImProjectsShouldNotBeFound("scoreCustomers.equals=" + UPDATED_SCORE_CUSTOMERS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreCustomersIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreCustomers in DEFAULT_SCORE_CUSTOMERS or UPDATED_SCORE_CUSTOMERS
        defaultImProjectsShouldBeFound("scoreCustomers.in=" + DEFAULT_SCORE_CUSTOMERS + "," + UPDATED_SCORE_CUSTOMERS);

        // Get all the imProjectsList where scoreCustomers equals to UPDATED_SCORE_CUSTOMERS
        defaultImProjectsShouldNotBeFound("scoreCustomers.in=" + UPDATED_SCORE_CUSTOMERS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreCustomersIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreCustomers is not null
        defaultImProjectsShouldBeFound("scoreCustomers.specified=true");

        // Get all the imProjectsList where scoreCustomers is null
        defaultImProjectsShouldNotBeFound("scoreCustomers.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreFinanceCostIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreFinanceCost equals to DEFAULT_SCORE_FINANCE_COST
        defaultImProjectsShouldBeFound("scoreFinanceCost.equals=" + DEFAULT_SCORE_FINANCE_COST);

        // Get all the imProjectsList where scoreFinanceCost equals to UPDATED_SCORE_FINANCE_COST
        defaultImProjectsShouldNotBeFound("scoreFinanceCost.equals=" + UPDATED_SCORE_FINANCE_COST);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreFinanceCostIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreFinanceCost in DEFAULT_SCORE_FINANCE_COST or UPDATED_SCORE_FINANCE_COST
        defaultImProjectsShouldBeFound("scoreFinanceCost.in=" + DEFAULT_SCORE_FINANCE_COST + "," + UPDATED_SCORE_FINANCE_COST);

        // Get all the imProjectsList where scoreFinanceCost equals to UPDATED_SCORE_FINANCE_COST
        defaultImProjectsShouldNotBeFound("scoreFinanceCost.in=" + UPDATED_SCORE_FINANCE_COST);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreFinanceCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreFinanceCost is not null
        defaultImProjectsShouldBeFound("scoreFinanceCost.specified=true");

        // Get all the imProjectsList where scoreFinanceCost is null
        defaultImProjectsShouldNotBeFound("scoreFinanceCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostBillsPlannedIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costBillsPlanned equals to DEFAULT_COST_BILLS_PLANNED
        defaultImProjectsShouldBeFound("costBillsPlanned.equals=" + DEFAULT_COST_BILLS_PLANNED);

        // Get all the imProjectsList where costBillsPlanned equals to UPDATED_COST_BILLS_PLANNED
        defaultImProjectsShouldNotBeFound("costBillsPlanned.equals=" + UPDATED_COST_BILLS_PLANNED);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostBillsPlannedIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costBillsPlanned in DEFAULT_COST_BILLS_PLANNED or UPDATED_COST_BILLS_PLANNED
        defaultImProjectsShouldBeFound("costBillsPlanned.in=" + DEFAULT_COST_BILLS_PLANNED + "," + UPDATED_COST_BILLS_PLANNED);

        // Get all the imProjectsList where costBillsPlanned equals to UPDATED_COST_BILLS_PLANNED
        defaultImProjectsShouldNotBeFound("costBillsPlanned.in=" + UPDATED_COST_BILLS_PLANNED);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostBillsPlannedIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costBillsPlanned is not null
        defaultImProjectsShouldBeFound("costBillsPlanned.specified=true");

        // Get all the imProjectsList where costBillsPlanned is null
        defaultImProjectsShouldNotBeFound("costBillsPlanned.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpensesPlannedIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpensesPlanned equals to DEFAULT_COST_EXPENSES_PLANNED
        defaultImProjectsShouldBeFound("costExpensesPlanned.equals=" + DEFAULT_COST_EXPENSES_PLANNED);

        // Get all the imProjectsList where costExpensesPlanned equals to UPDATED_COST_EXPENSES_PLANNED
        defaultImProjectsShouldNotBeFound("costExpensesPlanned.equals=" + UPDATED_COST_EXPENSES_PLANNED);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpensesPlannedIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpensesPlanned in DEFAULT_COST_EXPENSES_PLANNED or UPDATED_COST_EXPENSES_PLANNED
        defaultImProjectsShouldBeFound("costExpensesPlanned.in=" + DEFAULT_COST_EXPENSES_PLANNED + "," + UPDATED_COST_EXPENSES_PLANNED);

        // Get all the imProjectsList where costExpensesPlanned equals to UPDATED_COST_EXPENSES_PLANNED
        defaultImProjectsShouldNotBeFound("costExpensesPlanned.in=" + UPDATED_COST_EXPENSES_PLANNED);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCostExpensesPlannedIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where costExpensesPlanned is not null
        defaultImProjectsShouldBeFound("costExpensesPlanned.specified=true");

        // Get all the imProjectsList where costExpensesPlanned is null
        defaultImProjectsShouldNotBeFound("costExpensesPlanned.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreRiskIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreRisk equals to DEFAULT_SCORE_RISK
        defaultImProjectsShouldBeFound("scoreRisk.equals=" + DEFAULT_SCORE_RISK);

        // Get all the imProjectsList where scoreRisk equals to UPDATED_SCORE_RISK
        defaultImProjectsShouldNotBeFound("scoreRisk.equals=" + UPDATED_SCORE_RISK);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreRiskIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreRisk in DEFAULT_SCORE_RISK or UPDATED_SCORE_RISK
        defaultImProjectsShouldBeFound("scoreRisk.in=" + DEFAULT_SCORE_RISK + "," + UPDATED_SCORE_RISK);

        // Get all the imProjectsList where scoreRisk equals to UPDATED_SCORE_RISK
        defaultImProjectsShouldNotBeFound("scoreRisk.in=" + UPDATED_SCORE_RISK);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreRiskIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreRisk is not null
        defaultImProjectsShouldBeFound("scoreRisk.specified=true");

        // Get all the imProjectsList where scoreRisk is null
        defaultImProjectsShouldNotBeFound("scoreRisk.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreCapabilitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreCapabilities equals to DEFAULT_SCORE_CAPABILITIES
        defaultImProjectsShouldBeFound("scoreCapabilities.equals=" + DEFAULT_SCORE_CAPABILITIES);

        // Get all the imProjectsList where scoreCapabilities equals to UPDATED_SCORE_CAPABILITIES
        defaultImProjectsShouldNotBeFound("scoreCapabilities.equals=" + UPDATED_SCORE_CAPABILITIES);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreCapabilitiesIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreCapabilities in DEFAULT_SCORE_CAPABILITIES or UPDATED_SCORE_CAPABILITIES
        defaultImProjectsShouldBeFound("scoreCapabilities.in=" + DEFAULT_SCORE_CAPABILITIES + "," + UPDATED_SCORE_CAPABILITIES);

        // Get all the imProjectsList where scoreCapabilities equals to UPDATED_SCORE_CAPABILITIES
        defaultImProjectsShouldNotBeFound("scoreCapabilities.in=" + UPDATED_SCORE_CAPABILITIES);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreCapabilitiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreCapabilities is not null
        defaultImProjectsShouldBeFound("scoreCapabilities.specified=true");

        // Get all the imProjectsList where scoreCapabilities is null
        defaultImProjectsShouldNotBeFound("scoreCapabilities.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreEinanceRoiIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreEinanceRoi equals to DEFAULT_SCORE_EINANCE_ROI
        defaultImProjectsShouldBeFound("scoreEinanceRoi.equals=" + DEFAULT_SCORE_EINANCE_ROI);

        // Get all the imProjectsList where scoreEinanceRoi equals to UPDATED_SCORE_EINANCE_ROI
        defaultImProjectsShouldNotBeFound("scoreEinanceRoi.equals=" + UPDATED_SCORE_EINANCE_ROI);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreEinanceRoiIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreEinanceRoi in DEFAULT_SCORE_EINANCE_ROI or UPDATED_SCORE_EINANCE_ROI
        defaultImProjectsShouldBeFound("scoreEinanceRoi.in=" + DEFAULT_SCORE_EINANCE_ROI + "," + UPDATED_SCORE_EINANCE_ROI);

        // Get all the imProjectsList where scoreEinanceRoi equals to UPDATED_SCORE_EINANCE_ROI
        defaultImProjectsShouldNotBeFound("scoreEinanceRoi.in=" + UPDATED_SCORE_EINANCE_ROI);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreEinanceRoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreEinanceRoi is not null
        defaultImProjectsShouldBeFound("scoreEinanceRoi.specified=true");

        // Get all the imProjectsList where scoreEinanceRoi is null
        defaultImProjectsShouldNotBeFound("scoreEinanceRoi.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectUserwiseBoardIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectUserwiseBoard equals to DEFAULT_PROJECT_USERWISE_BOARD
        defaultImProjectsShouldBeFound("projectUserwiseBoard.equals=" + DEFAULT_PROJECT_USERWISE_BOARD);

        // Get all the imProjectsList where projectUserwiseBoard equals to UPDATED_PROJECT_USERWISE_BOARD
        defaultImProjectsShouldNotBeFound("projectUserwiseBoard.equals=" + UPDATED_PROJECT_USERWISE_BOARD);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectUserwiseBoardIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectUserwiseBoard in DEFAULT_PROJECT_USERWISE_BOARD or UPDATED_PROJECT_USERWISE_BOARD
        defaultImProjectsShouldBeFound("projectUserwiseBoard.in=" + DEFAULT_PROJECT_USERWISE_BOARD + "," + UPDATED_PROJECT_USERWISE_BOARD);

        // Get all the imProjectsList where projectUserwiseBoard equals to UPDATED_PROJECT_USERWISE_BOARD
        defaultImProjectsShouldNotBeFound("projectUserwiseBoard.in=" + UPDATED_PROJECT_USERWISE_BOARD);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectUserwiseBoardIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectUserwiseBoard is not null
        defaultImProjectsShouldBeFound("projectUserwiseBoard.specified=true");

        // Get all the imProjectsList where projectUserwiseBoard is null
        defaultImProjectsShouldNotBeFound("projectUserwiseBoard.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringNextdayIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringNextday equals to DEFAULT_PROJECT_BRING_NEXTDAY
        defaultImProjectsShouldBeFound("projectBringNextday.equals=" + DEFAULT_PROJECT_BRING_NEXTDAY);

        // Get all the imProjectsList where projectBringNextday equals to UPDATED_PROJECT_BRING_NEXTDAY
        defaultImProjectsShouldNotBeFound("projectBringNextday.equals=" + UPDATED_PROJECT_BRING_NEXTDAY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringNextdayIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringNextday in DEFAULT_PROJECT_BRING_NEXTDAY or UPDATED_PROJECT_BRING_NEXTDAY
        defaultImProjectsShouldBeFound("projectBringNextday.in=" + DEFAULT_PROJECT_BRING_NEXTDAY + "," + UPDATED_PROJECT_BRING_NEXTDAY);

        // Get all the imProjectsList where projectBringNextday equals to UPDATED_PROJECT_BRING_NEXTDAY
        defaultImProjectsShouldNotBeFound("projectBringNextday.in=" + UPDATED_PROJECT_BRING_NEXTDAY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringNextdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringNextday is not null
        defaultImProjectsShouldBeFound("projectBringNextday.specified=true");

        // Get all the imProjectsList where projectBringNextday is null
        defaultImProjectsShouldNotBeFound("projectBringNextday.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringNextdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringNextday greater than or equals to DEFAULT_PROJECT_BRING_NEXTDAY
        defaultImProjectsShouldBeFound("projectBringNextday.greaterOrEqualThan=" + DEFAULT_PROJECT_BRING_NEXTDAY);

        // Get all the imProjectsList where projectBringNextday greater than or equals to UPDATED_PROJECT_BRING_NEXTDAY
        defaultImProjectsShouldNotBeFound("projectBringNextday.greaterOrEqualThan=" + UPDATED_PROJECT_BRING_NEXTDAY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringNextdayIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringNextday less than or equals to DEFAULT_PROJECT_BRING_NEXTDAY
        defaultImProjectsShouldNotBeFound("projectBringNextday.lessThan=" + DEFAULT_PROJECT_BRING_NEXTDAY);

        // Get all the imProjectsList where projectBringNextday less than or equals to UPDATED_PROJECT_BRING_NEXTDAY
        defaultImProjectsShouldBeFound("projectBringNextday.lessThan=" + UPDATED_PROJECT_BRING_NEXTDAY);
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectBringSameboardIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringSameboard equals to DEFAULT_PROJECT_BRING_SAMEBOARD
        defaultImProjectsShouldBeFound("projectBringSameboard.equals=" + DEFAULT_PROJECT_BRING_SAMEBOARD);

        // Get all the imProjectsList where projectBringSameboard equals to UPDATED_PROJECT_BRING_SAMEBOARD
        defaultImProjectsShouldNotBeFound("projectBringSameboard.equals=" + UPDATED_PROJECT_BRING_SAMEBOARD);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringSameboardIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringSameboard in DEFAULT_PROJECT_BRING_SAMEBOARD or UPDATED_PROJECT_BRING_SAMEBOARD
        defaultImProjectsShouldBeFound("projectBringSameboard.in=" + DEFAULT_PROJECT_BRING_SAMEBOARD + "," + UPDATED_PROJECT_BRING_SAMEBOARD);

        // Get all the imProjectsList where projectBringSameboard equals to UPDATED_PROJECT_BRING_SAMEBOARD
        defaultImProjectsShouldNotBeFound("projectBringSameboard.in=" + UPDATED_PROJECT_BRING_SAMEBOARD);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringSameboardIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringSameboard is not null
        defaultImProjectsShouldBeFound("projectBringSameboard.specified=true");

        // Get all the imProjectsList where projectBringSameboard is null
        defaultImProjectsShouldNotBeFound("projectBringSameboard.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboardEverytimeIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboardEverytime equals to DEFAULT_PROJECT_NEWBOARD_EVERYTIME
        defaultImProjectsShouldBeFound("projectNewboardEverytime.equals=" + DEFAULT_PROJECT_NEWBOARD_EVERYTIME);

        // Get all the imProjectsList where projectNewboardEverytime equals to UPDATED_PROJECT_NEWBOARD_EVERYTIME
        defaultImProjectsShouldNotBeFound("projectNewboardEverytime.equals=" + UPDATED_PROJECT_NEWBOARD_EVERYTIME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboardEverytimeIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboardEverytime in DEFAULT_PROJECT_NEWBOARD_EVERYTIME or UPDATED_PROJECT_NEWBOARD_EVERYTIME
        defaultImProjectsShouldBeFound("projectNewboardEverytime.in=" + DEFAULT_PROJECT_NEWBOARD_EVERYTIME + "," + UPDATED_PROJECT_NEWBOARD_EVERYTIME);

        // Get all the imProjectsList where projectNewboardEverytime equals to UPDATED_PROJECT_NEWBOARD_EVERYTIME
        defaultImProjectsShouldNotBeFound("projectNewboardEverytime.in=" + UPDATED_PROJECT_NEWBOARD_EVERYTIME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboardEverytimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboardEverytime is not null
        defaultImProjectsShouldBeFound("projectNewboardEverytime.specified=true");

        // Get all the imProjectsList where projectNewboardEverytime is null
        defaultImProjectsShouldNotBeFound("projectNewboardEverytime.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectUserwiseBoard2IsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectUserwiseBoard2 equals to DEFAULT_PROJECT_USERWISE_BOARD_2
        defaultImProjectsShouldBeFound("projectUserwiseBoard2.equals=" + DEFAULT_PROJECT_USERWISE_BOARD_2);

        // Get all the imProjectsList where projectUserwiseBoard2 equals to UPDATED_PROJECT_USERWISE_BOARD_2
        defaultImProjectsShouldNotBeFound("projectUserwiseBoard2.equals=" + UPDATED_PROJECT_USERWISE_BOARD_2);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectUserwiseBoard2IsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectUserwiseBoard2 in DEFAULT_PROJECT_USERWISE_BOARD_2 or UPDATED_PROJECT_USERWISE_BOARD_2
        defaultImProjectsShouldBeFound("projectUserwiseBoard2.in=" + DEFAULT_PROJECT_USERWISE_BOARD_2 + "," + UPDATED_PROJECT_USERWISE_BOARD_2);

        // Get all the imProjectsList where projectUserwiseBoard2 equals to UPDATED_PROJECT_USERWISE_BOARD_2
        defaultImProjectsShouldNotBeFound("projectUserwiseBoard2.in=" + UPDATED_PROJECT_USERWISE_BOARD_2);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectUserwiseBoard2IsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectUserwiseBoard2 is not null
        defaultImProjectsShouldBeFound("projectUserwiseBoard2.specified=true");

        // Get all the imProjectsList where projectUserwiseBoard2 is null
        defaultImProjectsShouldNotBeFound("projectUserwiseBoard2.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringSameboard2IsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringSameboard2 equals to DEFAULT_PROJECT_BRING_SAMEBOARD_2
        defaultImProjectsShouldBeFound("projectBringSameboard2.equals=" + DEFAULT_PROJECT_BRING_SAMEBOARD_2);

        // Get all the imProjectsList where projectBringSameboard2 equals to UPDATED_PROJECT_BRING_SAMEBOARD_2
        defaultImProjectsShouldNotBeFound("projectBringSameboard2.equals=" + UPDATED_PROJECT_BRING_SAMEBOARD_2);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringSameboard2IsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringSameboard2 in DEFAULT_PROJECT_BRING_SAMEBOARD_2 or UPDATED_PROJECT_BRING_SAMEBOARD_2
        defaultImProjectsShouldBeFound("projectBringSameboard2.in=" + DEFAULT_PROJECT_BRING_SAMEBOARD_2 + "," + UPDATED_PROJECT_BRING_SAMEBOARD_2);

        // Get all the imProjectsList where projectBringSameboard2 equals to UPDATED_PROJECT_BRING_SAMEBOARD_2
        defaultImProjectsShouldNotBeFound("projectBringSameboard2.in=" + UPDATED_PROJECT_BRING_SAMEBOARD_2);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectBringSameboard2IsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectBringSameboard2 is not null
        defaultImProjectsShouldBeFound("projectBringSameboard2.specified=true");

        // Get all the imProjectsList where projectBringSameboard2 is null
        defaultImProjectsShouldNotBeFound("projectBringSameboard2.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboard2EverytimeIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboard2Everytime equals to DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME
        defaultImProjectsShouldBeFound("projectNewboard2Everytime.equals=" + DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME);

        // Get all the imProjectsList where projectNewboard2Everytime equals to UPDATED_PROJECT_NEWBOARD_2_EVERYTIME
        defaultImProjectsShouldNotBeFound("projectNewboard2Everytime.equals=" + UPDATED_PROJECT_NEWBOARD_2_EVERYTIME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboard2EverytimeIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboard2Everytime in DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME or UPDATED_PROJECT_NEWBOARD_2_EVERYTIME
        defaultImProjectsShouldBeFound("projectNewboard2Everytime.in=" + DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME + "," + UPDATED_PROJECT_NEWBOARD_2_EVERYTIME);

        // Get all the imProjectsList where projectNewboard2Everytime equals to UPDATED_PROJECT_NEWBOARD_2_EVERYTIME
        defaultImProjectsShouldNotBeFound("projectNewboard2Everytime.in=" + UPDATED_PROJECT_NEWBOARD_2_EVERYTIME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboard2EverytimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboard2Everytime is not null
        defaultImProjectsShouldBeFound("projectNewboard2Everytime.specified=true");

        // Get all the imProjectsList where projectNewboard2Everytime is null
        defaultImProjectsShouldNotBeFound("projectNewboard2Everytime.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboard2EverytimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboard2Everytime greater than or equals to DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME
        defaultImProjectsShouldBeFound("projectNewboard2Everytime.greaterOrEqualThan=" + DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME);

        // Get all the imProjectsList where projectNewboard2Everytime greater than or equals to UPDATED_PROJECT_NEWBOARD_2_EVERYTIME
        defaultImProjectsShouldNotBeFound("projectNewboard2Everytime.greaterOrEqualThan=" + UPDATED_PROJECT_NEWBOARD_2_EVERYTIME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboard2EverytimeIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboard2Everytime less than or equals to DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME
        defaultImProjectsShouldNotBeFound("projectNewboard2Everytime.lessThan=" + DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME);

        // Get all the imProjectsList where projectNewboard2Everytime less than or equals to UPDATED_PROJECT_NEWBOARD_2_EVERYTIME
        defaultImProjectsShouldBeFound("projectNewboard2Everytime.lessThan=" + UPDATED_PROJECT_NEWBOARD_2_EVERYTIME);
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboard2AlwaysIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboard2Always equals to DEFAULT_PROJECT_NEWBOARD_2_ALWAYS
        defaultImProjectsShouldBeFound("projectNewboard2Always.equals=" + DEFAULT_PROJECT_NEWBOARD_2_ALWAYS);

        // Get all the imProjectsList where projectNewboard2Always equals to UPDATED_PROJECT_NEWBOARD_2_ALWAYS
        defaultImProjectsShouldNotBeFound("projectNewboard2Always.equals=" + UPDATED_PROJECT_NEWBOARD_2_ALWAYS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboard2AlwaysIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboard2Always in DEFAULT_PROJECT_NEWBOARD_2_ALWAYS or UPDATED_PROJECT_NEWBOARD_2_ALWAYS
        defaultImProjectsShouldBeFound("projectNewboard2Always.in=" + DEFAULT_PROJECT_NEWBOARD_2_ALWAYS + "," + UPDATED_PROJECT_NEWBOARD_2_ALWAYS);

        // Get all the imProjectsList where projectNewboard2Always equals to UPDATED_PROJECT_NEWBOARD_2_ALWAYS
        defaultImProjectsShouldNotBeFound("projectNewboard2Always.in=" + UPDATED_PROJECT_NEWBOARD_2_ALWAYS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectNewboard2AlwaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectNewboard2Always is not null
        defaultImProjectsShouldBeFound("projectNewboard2Always.specified=true");

        // Get all the imProjectsList where projectNewboard2Always is null
        defaultImProjectsShouldNotBeFound("projectNewboard2Always.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectReportWeeklyIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectReportWeekly equals to DEFAULT_PROJECT_REPORT_WEEKLY
        defaultImProjectsShouldBeFound("projectReportWeekly.equals=" + DEFAULT_PROJECT_REPORT_WEEKLY);

        // Get all the imProjectsList where projectReportWeekly equals to UPDATED_PROJECT_REPORT_WEEKLY
        defaultImProjectsShouldNotBeFound("projectReportWeekly.equals=" + UPDATED_PROJECT_REPORT_WEEKLY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectReportWeeklyIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectReportWeekly in DEFAULT_PROJECT_REPORT_WEEKLY or UPDATED_PROJECT_REPORT_WEEKLY
        defaultImProjectsShouldBeFound("projectReportWeekly.in=" + DEFAULT_PROJECT_REPORT_WEEKLY + "," + UPDATED_PROJECT_REPORT_WEEKLY);

        // Get all the imProjectsList where projectReportWeekly equals to UPDATED_PROJECT_REPORT_WEEKLY
        defaultImProjectsShouldNotBeFound("projectReportWeekly.in=" + UPDATED_PROJECT_REPORT_WEEKLY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectReportWeeklyIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where projectReportWeekly is not null
        defaultImProjectsShouldBeFound("projectReportWeekly.specified=true");

        // Get all the imProjectsList where projectReportWeekly is null
        defaultImProjectsShouldNotBeFound("projectReportWeekly.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreGainIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreGain equals to DEFAULT_SCORE_GAIN
        defaultImProjectsShouldBeFound("scoreGain.equals=" + DEFAULT_SCORE_GAIN);

        // Get all the imProjectsList where scoreGain equals to UPDATED_SCORE_GAIN
        defaultImProjectsShouldNotBeFound("scoreGain.equals=" + UPDATED_SCORE_GAIN);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreGainIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreGain in DEFAULT_SCORE_GAIN or UPDATED_SCORE_GAIN
        defaultImProjectsShouldBeFound("scoreGain.in=" + DEFAULT_SCORE_GAIN + "," + UPDATED_SCORE_GAIN);

        // Get all the imProjectsList where scoreGain equals to UPDATED_SCORE_GAIN
        defaultImProjectsShouldNotBeFound("scoreGain.in=" + UPDATED_SCORE_GAIN);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreGainIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreGain is not null
        defaultImProjectsShouldBeFound("scoreGain.specified=true");

        // Get all the imProjectsList where scoreGain is null
        defaultImProjectsShouldNotBeFound("scoreGain.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreLossIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreLoss equals to DEFAULT_SCORE_LOSS
        defaultImProjectsShouldBeFound("scoreLoss.equals=" + DEFAULT_SCORE_LOSS);

        // Get all the imProjectsList where scoreLoss equals to UPDATED_SCORE_LOSS
        defaultImProjectsShouldNotBeFound("scoreLoss.equals=" + UPDATED_SCORE_LOSS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreLossIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreLoss in DEFAULT_SCORE_LOSS or UPDATED_SCORE_LOSS
        defaultImProjectsShouldBeFound("scoreLoss.in=" + DEFAULT_SCORE_LOSS + "," + UPDATED_SCORE_LOSS);

        // Get all the imProjectsList where scoreLoss equals to UPDATED_SCORE_LOSS
        defaultImProjectsShouldNotBeFound("scoreLoss.in=" + UPDATED_SCORE_LOSS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreLossIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreLoss is not null
        defaultImProjectsShouldBeFound("scoreLoss.specified=true");

        // Get all the imProjectsList where scoreLoss is null
        defaultImProjectsShouldNotBeFound("scoreLoss.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreDeliveryIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreDelivery equals to DEFAULT_SCORE_DELIVERY
        defaultImProjectsShouldBeFound("scoreDelivery.equals=" + DEFAULT_SCORE_DELIVERY);

        // Get all the imProjectsList where scoreDelivery equals to UPDATED_SCORE_DELIVERY
        defaultImProjectsShouldNotBeFound("scoreDelivery.equals=" + UPDATED_SCORE_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreDeliveryIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreDelivery in DEFAULT_SCORE_DELIVERY or UPDATED_SCORE_DELIVERY
        defaultImProjectsShouldBeFound("scoreDelivery.in=" + DEFAULT_SCORE_DELIVERY + "," + UPDATED_SCORE_DELIVERY);

        // Get all the imProjectsList where scoreDelivery equals to UPDATED_SCORE_DELIVERY
        defaultImProjectsShouldNotBeFound("scoreDelivery.in=" + UPDATED_SCORE_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreDeliveryIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreDelivery is not null
        defaultImProjectsShouldBeFound("scoreDelivery.specified=true");

        // Get all the imProjectsList where scoreDelivery is null
        defaultImProjectsShouldNotBeFound("scoreDelivery.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreOperationsIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreOperations equals to DEFAULT_SCORE_OPERATIONS
        defaultImProjectsShouldBeFound("scoreOperations.equals=" + DEFAULT_SCORE_OPERATIONS);

        // Get all the imProjectsList where scoreOperations equals to UPDATED_SCORE_OPERATIONS
        defaultImProjectsShouldNotBeFound("scoreOperations.equals=" + UPDATED_SCORE_OPERATIONS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreOperationsIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreOperations in DEFAULT_SCORE_OPERATIONS or UPDATED_SCORE_OPERATIONS
        defaultImProjectsShouldBeFound("scoreOperations.in=" + DEFAULT_SCORE_OPERATIONS + "," + UPDATED_SCORE_OPERATIONS);

        // Get all the imProjectsList where scoreOperations equals to UPDATED_SCORE_OPERATIONS
        defaultImProjectsShouldNotBeFound("scoreOperations.in=" + UPDATED_SCORE_OPERATIONS);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreOperationsIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreOperations is not null
        defaultImProjectsShouldBeFound("scoreOperations.specified=true");

        // Get all the imProjectsList where scoreOperations is null
        defaultImProjectsShouldNotBeFound("scoreOperations.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreWhyIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreWhy equals to DEFAULT_SCORE_WHY
        defaultImProjectsShouldBeFound("scoreWhy.equals=" + DEFAULT_SCORE_WHY);

        // Get all the imProjectsList where scoreWhy equals to UPDATED_SCORE_WHY
        defaultImProjectsShouldNotBeFound("scoreWhy.equals=" + UPDATED_SCORE_WHY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreWhyIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreWhy in DEFAULT_SCORE_WHY or UPDATED_SCORE_WHY
        defaultImProjectsShouldBeFound("scoreWhy.in=" + DEFAULT_SCORE_WHY + "," + UPDATED_SCORE_WHY);

        // Get all the imProjectsList where scoreWhy equals to UPDATED_SCORE_WHY
        defaultImProjectsShouldNotBeFound("scoreWhy.in=" + UPDATED_SCORE_WHY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreWhyIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreWhy is not null
        defaultImProjectsShouldBeFound("scoreWhy.specified=true");

        // Get all the imProjectsList where scoreWhy is null
        defaultImProjectsShouldNotBeFound("scoreWhy.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreWhyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreWhy greater than or equals to DEFAULT_SCORE_WHY
        defaultImProjectsShouldBeFound("scoreWhy.greaterOrEqualThan=" + DEFAULT_SCORE_WHY);

        // Get all the imProjectsList where scoreWhy greater than or equals to UPDATED_SCORE_WHY
        defaultImProjectsShouldNotBeFound("scoreWhy.greaterOrEqualThan=" + UPDATED_SCORE_WHY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByScoreWhyIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where scoreWhy less than or equals to DEFAULT_SCORE_WHY
        defaultImProjectsShouldNotBeFound("scoreWhy.lessThan=" + DEFAULT_SCORE_WHY);

        // Get all the imProjectsList where scoreWhy less than or equals to UPDATED_SCORE_WHY
        defaultImProjectsShouldBeFound("scoreWhy.lessThan=" + UPDATED_SCORE_WHY);
    }


    @Test
    @Transactional
    public void getAllImProjectsByJavaServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where javaServices equals to DEFAULT_JAVA_SERVICES
        defaultImProjectsShouldBeFound("javaServices.equals=" + DEFAULT_JAVA_SERVICES);

        // Get all the imProjectsList where javaServices equals to UPDATED_JAVA_SERVICES
        defaultImProjectsShouldNotBeFound("javaServices.equals=" + UPDATED_JAVA_SERVICES);
    }

    @Test
    @Transactional
    public void getAllImProjectsByJavaServicesIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where javaServices in DEFAULT_JAVA_SERVICES or UPDATED_JAVA_SERVICES
        defaultImProjectsShouldBeFound("javaServices.in=" + DEFAULT_JAVA_SERVICES + "," + UPDATED_JAVA_SERVICES);

        // Get all the imProjectsList where javaServices equals to UPDATED_JAVA_SERVICES
        defaultImProjectsShouldNotBeFound("javaServices.in=" + UPDATED_JAVA_SERVICES);
    }

    @Test
    @Transactional
    public void getAllImProjectsByJavaServicesIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where javaServices is not null
        defaultImProjectsShouldBeFound("javaServices.specified=true");

        // Get all the imProjectsList where javaServices is null
        defaultImProjectsShouldNotBeFound("javaServices.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByNetServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where netServices equals to DEFAULT_NET_SERVICES
        defaultImProjectsShouldBeFound("netServices.equals=" + DEFAULT_NET_SERVICES);

        // Get all the imProjectsList where netServices equals to UPDATED_NET_SERVICES
        defaultImProjectsShouldNotBeFound("netServices.equals=" + UPDATED_NET_SERVICES);
    }

    @Test
    @Transactional
    public void getAllImProjectsByNetServicesIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where netServices in DEFAULT_NET_SERVICES or UPDATED_NET_SERVICES
        defaultImProjectsShouldBeFound("netServices.in=" + DEFAULT_NET_SERVICES + "," + UPDATED_NET_SERVICES);

        // Get all the imProjectsList where netServices equals to UPDATED_NET_SERVICES
        defaultImProjectsShouldNotBeFound("netServices.in=" + UPDATED_NET_SERVICES);
    }

    @Test
    @Transactional
    public void getAllImProjectsByNetServicesIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where netServices is not null
        defaultImProjectsShouldBeFound("netServices.specified=true");

        // Get all the imProjectsList where netServices is null
        defaultImProjectsShouldNotBeFound("netServices.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCollectionLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where collectionLink equals to DEFAULT_COLLECTION_LINK
        defaultImProjectsShouldBeFound("collectionLink.equals=" + DEFAULT_COLLECTION_LINK);

        // Get all the imProjectsList where collectionLink equals to UPDATED_COLLECTION_LINK
        defaultImProjectsShouldNotBeFound("collectionLink.equals=" + UPDATED_COLLECTION_LINK);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCollectionLinkIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where collectionLink in DEFAULT_COLLECTION_LINK or UPDATED_COLLECTION_LINK
        defaultImProjectsShouldBeFound("collectionLink.in=" + DEFAULT_COLLECTION_LINK + "," + UPDATED_COLLECTION_LINK);

        // Get all the imProjectsList where collectionLink equals to UPDATED_COLLECTION_LINK
        defaultImProjectsShouldNotBeFound("collectionLink.in=" + UPDATED_COLLECTION_LINK);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCollectionLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where collectionLink is not null
        defaultImProjectsShouldBeFound("collectionLink.specified=true");

        // Get all the imProjectsList where collectionLink is null
        defaultImProjectsShouldNotBeFound("collectionLink.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingLink equals to DEFAULT_TRAINING_LINK
        defaultImProjectsShouldBeFound("trainingLink.equals=" + DEFAULT_TRAINING_LINK);

        // Get all the imProjectsList where trainingLink equals to UPDATED_TRAINING_LINK
        defaultImProjectsShouldNotBeFound("trainingLink.equals=" + UPDATED_TRAINING_LINK);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingLinkIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingLink in DEFAULT_TRAINING_LINK or UPDATED_TRAINING_LINK
        defaultImProjectsShouldBeFound("trainingLink.in=" + DEFAULT_TRAINING_LINK + "," + UPDATED_TRAINING_LINK);

        // Get all the imProjectsList where trainingLink equals to UPDATED_TRAINING_LINK
        defaultImProjectsShouldNotBeFound("trainingLink.in=" + UPDATED_TRAINING_LINK);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingLink is not null
        defaultImProjectsShouldBeFound("trainingLink.specified=true");

        // Get all the imProjectsList where trainingLink is null
        defaultImProjectsShouldNotBeFound("trainingLink.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByCollectionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where collectionName equals to DEFAULT_COLLECTION_NAME
        defaultImProjectsShouldBeFound("collectionName.equals=" + DEFAULT_COLLECTION_NAME);

        // Get all the imProjectsList where collectionName equals to UPDATED_COLLECTION_NAME
        defaultImProjectsShouldNotBeFound("collectionName.equals=" + UPDATED_COLLECTION_NAME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCollectionNameIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where collectionName in DEFAULT_COLLECTION_NAME or UPDATED_COLLECTION_NAME
        defaultImProjectsShouldBeFound("collectionName.in=" + DEFAULT_COLLECTION_NAME + "," + UPDATED_COLLECTION_NAME);

        // Get all the imProjectsList where collectionName equals to UPDATED_COLLECTION_NAME
        defaultImProjectsShouldNotBeFound("collectionName.in=" + UPDATED_COLLECTION_NAME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByCollectionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where collectionName is not null
        defaultImProjectsShouldBeFound("collectionName.specified=true");

        // Get all the imProjectsList where collectionName is null
        defaultImProjectsShouldNotBeFound("collectionName.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingNameIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingName equals to DEFAULT_TRAINING_NAME
        defaultImProjectsShouldBeFound("trainingName.equals=" + DEFAULT_TRAINING_NAME);

        // Get all the imProjectsList where trainingName equals to UPDATED_TRAINING_NAME
        defaultImProjectsShouldNotBeFound("trainingName.equals=" + UPDATED_TRAINING_NAME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingNameIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingName in DEFAULT_TRAINING_NAME or UPDATED_TRAINING_NAME
        defaultImProjectsShouldBeFound("trainingName.in=" + DEFAULT_TRAINING_NAME + "," + UPDATED_TRAINING_NAME);

        // Get all the imProjectsList where trainingName equals to UPDATED_TRAINING_NAME
        defaultImProjectsShouldNotBeFound("trainingName.in=" + UPDATED_TRAINING_NAME);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingName is not null
        defaultImProjectsShouldBeFound("trainingName.specified=true");

        // Get all the imProjectsList where trainingName is null
        defaultImProjectsShouldNotBeFound("trainingName.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingDocIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingDoc equals to DEFAULT_TRAINING_DOC
        defaultImProjectsShouldBeFound("trainingDoc.equals=" + DEFAULT_TRAINING_DOC);

        // Get all the imProjectsList where trainingDoc equals to UPDATED_TRAINING_DOC
        defaultImProjectsShouldNotBeFound("trainingDoc.equals=" + UPDATED_TRAINING_DOC);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingDocIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingDoc in DEFAULT_TRAINING_DOC or UPDATED_TRAINING_DOC
        defaultImProjectsShouldBeFound("trainingDoc.in=" + DEFAULT_TRAINING_DOC + "," + UPDATED_TRAINING_DOC);

        // Get all the imProjectsList where trainingDoc equals to UPDATED_TRAINING_DOC
        defaultImProjectsShouldNotBeFound("trainingDoc.in=" + UPDATED_TRAINING_DOC);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTrainingDocIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where trainingDoc is not null
        defaultImProjectsShouldBeFound("trainingDoc.specified=true");

        // Get all the imProjectsList where trainingDoc is null
        defaultImProjectsShouldNotBeFound("trainingDoc.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByTestingRichtextIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where testingRichtext equals to DEFAULT_TESTING_RICHTEXT
        defaultImProjectsShouldBeFound("testingRichtext.equals=" + DEFAULT_TESTING_RICHTEXT);

        // Get all the imProjectsList where testingRichtext equals to UPDATED_TESTING_RICHTEXT
        defaultImProjectsShouldNotBeFound("testingRichtext.equals=" + UPDATED_TESTING_RICHTEXT);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTestingRichtextIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where testingRichtext in DEFAULT_TESTING_RICHTEXT or UPDATED_TESTING_RICHTEXT
        defaultImProjectsShouldBeFound("testingRichtext.in=" + DEFAULT_TESTING_RICHTEXT + "," + UPDATED_TESTING_RICHTEXT);

        // Get all the imProjectsList where testingRichtext equals to UPDATED_TESTING_RICHTEXT
        defaultImProjectsShouldNotBeFound("testingRichtext.in=" + UPDATED_TESTING_RICHTEXT);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTestingRichtextIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where testingRichtext is not null
        defaultImProjectsShouldBeFound("testingRichtext.specified=true");

        // Get all the imProjectsList where testingRichtext is null
        defaultImProjectsShouldNotBeFound("testingRichtext.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByTestingRichtextIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where testingRichtext greater than or equals to DEFAULT_TESTING_RICHTEXT
        defaultImProjectsShouldBeFound("testingRichtext.greaterOrEqualThan=" + DEFAULT_TESTING_RICHTEXT);

        // Get all the imProjectsList where testingRichtext greater than or equals to UPDATED_TESTING_RICHTEXT
        defaultImProjectsShouldNotBeFound("testingRichtext.greaterOrEqualThan=" + UPDATED_TESTING_RICHTEXT);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTestingRichtextIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where testingRichtext less than or equals to DEFAULT_TESTING_RICHTEXT
        defaultImProjectsShouldNotBeFound("testingRichtext.lessThan=" + DEFAULT_TESTING_RICHTEXT);

        // Get all the imProjectsList where testingRichtext less than or equals to UPDATED_TESTING_RICHTEXT
        defaultImProjectsShouldBeFound("testingRichtext.lessThan=" + UPDATED_TESTING_RICHTEXT);
    }


    @Test
    @Transactional
    public void getAllImProjectsByTemplateCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where templateCategory equals to DEFAULT_TEMPLATE_CATEGORY
        defaultImProjectsShouldBeFound("templateCategory.equals=" + DEFAULT_TEMPLATE_CATEGORY);

        // Get all the imProjectsList where templateCategory equals to UPDATED_TEMPLATE_CATEGORY
        defaultImProjectsShouldNotBeFound("templateCategory.equals=" + UPDATED_TEMPLATE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTemplateCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where templateCategory in DEFAULT_TEMPLATE_CATEGORY or UPDATED_TEMPLATE_CATEGORY
        defaultImProjectsShouldBeFound("templateCategory.in=" + DEFAULT_TEMPLATE_CATEGORY + "," + UPDATED_TEMPLATE_CATEGORY);

        // Get all the imProjectsList where templateCategory equals to UPDATED_TEMPLATE_CATEGORY
        defaultImProjectsShouldNotBeFound("templateCategory.in=" + UPDATED_TEMPLATE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByTemplateCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where templateCategory is not null
        defaultImProjectsShouldBeFound("templateCategory.specified=true");

        // Get all the imProjectsList where templateCategory is null
        defaultImProjectsShouldNotBeFound("templateCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBydTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dType equals to DEFAULT_D_TYPE
        defaultImProjectsShouldBeFound("dType.equals=" + DEFAULT_D_TYPE);

        // Get all the imProjectsList where dType equals to UPDATED_D_TYPE
        defaultImProjectsShouldNotBeFound("dType.equals=" + UPDATED_D_TYPE);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydTypeIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dType in DEFAULT_D_TYPE or UPDATED_D_TYPE
        defaultImProjectsShouldBeFound("dType.in=" + DEFAULT_D_TYPE + "," + UPDATED_D_TYPE);

        // Get all the imProjectsList where dType equals to UPDATED_D_TYPE
        defaultImProjectsShouldNotBeFound("dType.in=" + UPDATED_D_TYPE);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dType is not null
        defaultImProjectsShouldBeFound("dType.specified=true");

        // Get all the imProjectsList where dType is null
        defaultImProjectsShouldNotBeFound("dType.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBydTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dType greater than or equals to DEFAULT_D_TYPE
        defaultImProjectsShouldBeFound("dType.greaterOrEqualThan=" + DEFAULT_D_TYPE);

        // Get all the imProjectsList where dType greater than or equals to UPDATED_D_TYPE
        defaultImProjectsShouldNotBeFound("dType.greaterOrEqualThan=" + UPDATED_D_TYPE);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dType less than or equals to DEFAULT_D_TYPE
        defaultImProjectsShouldNotBeFound("dType.lessThan=" + DEFAULT_D_TYPE);

        // Get all the imProjectsList where dType less than or equals to UPDATED_D_TYPE
        defaultImProjectsShouldBeFound("dType.lessThan=" + UPDATED_D_TYPE);
    }


    @Test
    @Transactional
    public void getAllImProjectsBydOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dOption equals to DEFAULT_D_OPTION
        defaultImProjectsShouldBeFound("dOption.equals=" + DEFAULT_D_OPTION);

        // Get all the imProjectsList where dOption equals to UPDATED_D_OPTION
        defaultImProjectsShouldNotBeFound("dOption.equals=" + UPDATED_D_OPTION);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydOptionIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dOption in DEFAULT_D_OPTION or UPDATED_D_OPTION
        defaultImProjectsShouldBeFound("dOption.in=" + DEFAULT_D_OPTION + "," + UPDATED_D_OPTION);

        // Get all the imProjectsList where dOption equals to UPDATED_D_OPTION
        defaultImProjectsShouldNotBeFound("dOption.in=" + UPDATED_D_OPTION);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydOptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dOption is not null
        defaultImProjectsShouldBeFound("dOption.specified=true");

        // Get all the imProjectsList where dOption is null
        defaultImProjectsShouldNotBeFound("dOption.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBydOptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dOption greater than or equals to DEFAULT_D_OPTION
        defaultImProjectsShouldBeFound("dOption.greaterOrEqualThan=" + DEFAULT_D_OPTION);

        // Get all the imProjectsList where dOption greater than or equals to UPDATED_D_OPTION
        defaultImProjectsShouldNotBeFound("dOption.greaterOrEqualThan=" + UPDATED_D_OPTION);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydOptionIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dOption less than or equals to DEFAULT_D_OPTION
        defaultImProjectsShouldNotBeFound("dOption.lessThan=" + DEFAULT_D_OPTION);

        // Get all the imProjectsList where dOption less than or equals to UPDATED_D_OPTION
        defaultImProjectsShouldBeFound("dOption.lessThan=" + UPDATED_D_OPTION);
    }


    @Test
    @Transactional
    public void getAllImProjectsBydFilterIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dFilter equals to DEFAULT_D_FILTER
        defaultImProjectsShouldBeFound("dFilter.equals=" + DEFAULT_D_FILTER);

        // Get all the imProjectsList where dFilter equals to UPDATED_D_FILTER
        defaultImProjectsShouldNotBeFound("dFilter.equals=" + UPDATED_D_FILTER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydFilterIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dFilter in DEFAULT_D_FILTER or UPDATED_D_FILTER
        defaultImProjectsShouldBeFound("dFilter.in=" + DEFAULT_D_FILTER + "," + UPDATED_D_FILTER);

        // Get all the imProjectsList where dFilter equals to UPDATED_D_FILTER
        defaultImProjectsShouldNotBeFound("dFilter.in=" + UPDATED_D_FILTER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydFilterIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dFilter is not null
        defaultImProjectsShouldBeFound("dFilter.specified=true");

        // Get all the imProjectsList where dFilter is null
        defaultImProjectsShouldNotBeFound("dFilter.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBydFilterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dFilter greater than or equals to DEFAULT_D_FILTER
        defaultImProjectsShouldBeFound("dFilter.greaterOrEqualThan=" + DEFAULT_D_FILTER);

        // Get all the imProjectsList where dFilter greater than or equals to UPDATED_D_FILTER
        defaultImProjectsShouldNotBeFound("dFilter.greaterOrEqualThan=" + UPDATED_D_FILTER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydFilterIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dFilter less than or equals to DEFAULT_D_FILTER
        defaultImProjectsShouldNotBeFound("dFilter.lessThan=" + DEFAULT_D_FILTER);

        // Get all the imProjectsList where dFilter less than or equals to UPDATED_D_FILTER
        defaultImProjectsShouldBeFound("dFilter.lessThan=" + UPDATED_D_FILTER);
    }


    @Test
    @Transactional
    public void getAllImProjectsBydIdIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dId equals to DEFAULT_D_ID
        defaultImProjectsShouldBeFound("dId.equals=" + DEFAULT_D_ID);

        // Get all the imProjectsList where dId equals to UPDATED_D_ID
        defaultImProjectsShouldNotBeFound("dId.equals=" + UPDATED_D_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydIdIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dId in DEFAULT_D_ID or UPDATED_D_ID
        defaultImProjectsShouldBeFound("dId.in=" + DEFAULT_D_ID + "," + UPDATED_D_ID);

        // Get all the imProjectsList where dId equals to UPDATED_D_ID
        defaultImProjectsShouldNotBeFound("dId.in=" + UPDATED_D_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dId is not null
        defaultImProjectsShouldBeFound("dId.specified=true");

        // Get all the imProjectsList where dId is null
        defaultImProjectsShouldNotBeFound("dId.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBydIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dId greater than or equals to DEFAULT_D_ID
        defaultImProjectsShouldBeFound("dId.greaterOrEqualThan=" + DEFAULT_D_ID);

        // Get all the imProjectsList where dId greater than or equals to UPDATED_D_ID
        defaultImProjectsShouldNotBeFound("dId.greaterOrEqualThan=" + UPDATED_D_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsBydIdIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where dId less than or equals to DEFAULT_D_ID
        defaultImProjectsShouldNotBeFound("dId.lessThan=" + DEFAULT_D_ID);

        // Get all the imProjectsList where dId less than or equals to UPDATED_D_ID
        defaultImProjectsShouldBeFound("dId.lessThan=" + UPDATED_D_ID);
    }


    @Test
    @Transactional
    public void getAllImProjectsBytTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tType equals to DEFAULT_T_TYPE
        defaultImProjectsShouldBeFound("tType.equals=" + DEFAULT_T_TYPE);

        // Get all the imProjectsList where tType equals to UPDATED_T_TYPE
        defaultImProjectsShouldNotBeFound("tType.equals=" + UPDATED_T_TYPE);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytTypeIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tType in DEFAULT_T_TYPE or UPDATED_T_TYPE
        defaultImProjectsShouldBeFound("tType.in=" + DEFAULT_T_TYPE + "," + UPDATED_T_TYPE);

        // Get all the imProjectsList where tType equals to UPDATED_T_TYPE
        defaultImProjectsShouldNotBeFound("tType.in=" + UPDATED_T_TYPE);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tType is not null
        defaultImProjectsShouldBeFound("tType.specified=true");

        // Get all the imProjectsList where tType is null
        defaultImProjectsShouldNotBeFound("tType.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBytTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tType greater than or equals to DEFAULT_T_TYPE
        defaultImProjectsShouldBeFound("tType.greaterOrEqualThan=" + DEFAULT_T_TYPE);

        // Get all the imProjectsList where tType greater than or equals to UPDATED_T_TYPE
        defaultImProjectsShouldNotBeFound("tType.greaterOrEqualThan=" + UPDATED_T_TYPE);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tType less than or equals to DEFAULT_T_TYPE
        defaultImProjectsShouldNotBeFound("tType.lessThan=" + DEFAULT_T_TYPE);

        // Get all the imProjectsList where tType less than or equals to UPDATED_T_TYPE
        defaultImProjectsShouldBeFound("tType.lessThan=" + UPDATED_T_TYPE);
    }


    @Test
    @Transactional
    public void getAllImProjectsBytOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tOption equals to DEFAULT_T_OPTION
        defaultImProjectsShouldBeFound("tOption.equals=" + DEFAULT_T_OPTION);

        // Get all the imProjectsList where tOption equals to UPDATED_T_OPTION
        defaultImProjectsShouldNotBeFound("tOption.equals=" + UPDATED_T_OPTION);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytOptionIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tOption in DEFAULT_T_OPTION or UPDATED_T_OPTION
        defaultImProjectsShouldBeFound("tOption.in=" + DEFAULT_T_OPTION + "," + UPDATED_T_OPTION);

        // Get all the imProjectsList where tOption equals to UPDATED_T_OPTION
        defaultImProjectsShouldNotBeFound("tOption.in=" + UPDATED_T_OPTION);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytOptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tOption is not null
        defaultImProjectsShouldBeFound("tOption.specified=true");

        // Get all the imProjectsList where tOption is null
        defaultImProjectsShouldNotBeFound("tOption.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBytOptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tOption greater than or equals to DEFAULT_T_OPTION
        defaultImProjectsShouldBeFound("tOption.greaterOrEqualThan=" + DEFAULT_T_OPTION);

        // Get all the imProjectsList where tOption greater than or equals to UPDATED_T_OPTION
        defaultImProjectsShouldNotBeFound("tOption.greaterOrEqualThan=" + UPDATED_T_OPTION);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytOptionIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tOption less than or equals to DEFAULT_T_OPTION
        defaultImProjectsShouldNotBeFound("tOption.lessThan=" + DEFAULT_T_OPTION);

        // Get all the imProjectsList where tOption less than or equals to UPDATED_T_OPTION
        defaultImProjectsShouldBeFound("tOption.lessThan=" + UPDATED_T_OPTION);
    }


    @Test
    @Transactional
    public void getAllImProjectsBytFilterIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tFilter equals to DEFAULT_T_FILTER
        defaultImProjectsShouldBeFound("tFilter.equals=" + DEFAULT_T_FILTER);

        // Get all the imProjectsList where tFilter equals to UPDATED_T_FILTER
        defaultImProjectsShouldNotBeFound("tFilter.equals=" + UPDATED_T_FILTER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytFilterIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tFilter in DEFAULT_T_FILTER or UPDATED_T_FILTER
        defaultImProjectsShouldBeFound("tFilter.in=" + DEFAULT_T_FILTER + "," + UPDATED_T_FILTER);

        // Get all the imProjectsList where tFilter equals to UPDATED_T_FILTER
        defaultImProjectsShouldNotBeFound("tFilter.in=" + UPDATED_T_FILTER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytFilterIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tFilter is not null
        defaultImProjectsShouldBeFound("tFilter.specified=true");

        // Get all the imProjectsList where tFilter is null
        defaultImProjectsShouldNotBeFound("tFilter.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBytFilterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tFilter greater than or equals to DEFAULT_T_FILTER
        defaultImProjectsShouldBeFound("tFilter.greaterOrEqualThan=" + DEFAULT_T_FILTER);

        // Get all the imProjectsList where tFilter greater than or equals to UPDATED_T_FILTER
        defaultImProjectsShouldNotBeFound("tFilter.greaterOrEqualThan=" + UPDATED_T_FILTER);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytFilterIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tFilter less than or equals to DEFAULT_T_FILTER
        defaultImProjectsShouldNotBeFound("tFilter.lessThan=" + DEFAULT_T_FILTER);

        // Get all the imProjectsList where tFilter less than or equals to UPDATED_T_FILTER
        defaultImProjectsShouldBeFound("tFilter.lessThan=" + UPDATED_T_FILTER);
    }


    @Test
    @Transactional
    public void getAllImProjectsBytIdIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tId equals to DEFAULT_T_ID
        defaultImProjectsShouldBeFound("tId.equals=" + DEFAULT_T_ID);

        // Get all the imProjectsList where tId equals to UPDATED_T_ID
        defaultImProjectsShouldNotBeFound("tId.equals=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytIdIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tId in DEFAULT_T_ID or UPDATED_T_ID
        defaultImProjectsShouldBeFound("tId.in=" + DEFAULT_T_ID + "," + UPDATED_T_ID);

        // Get all the imProjectsList where tId equals to UPDATED_T_ID
        defaultImProjectsShouldNotBeFound("tId.in=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tId is not null
        defaultImProjectsShouldBeFound("tId.specified=true");

        // Get all the imProjectsList where tId is null
        defaultImProjectsShouldNotBeFound("tId.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsBytIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tId greater than or equals to DEFAULT_T_ID
        defaultImProjectsShouldBeFound("tId.greaterOrEqualThan=" + DEFAULT_T_ID);

        // Get all the imProjectsList where tId greater than or equals to UPDATED_T_ID
        defaultImProjectsShouldNotBeFound("tId.greaterOrEqualThan=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    public void getAllImProjectsBytIdIsLessThanSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where tId less than or equals to DEFAULT_T_ID
        defaultImProjectsShouldNotBeFound("tId.lessThan=" + DEFAULT_T_ID);

        // Get all the imProjectsList where tId less than or equals to UPDATED_T_ID
        defaultImProjectsShouldBeFound("tId.lessThan=" + UPDATED_T_ID);
    }


    @Test
    @Transactional
    public void getAllImProjectsByRisktypeIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where risktype equals to DEFAULT_RISKTYPE
        defaultImProjectsShouldBeFound("risktype.equals=" + DEFAULT_RISKTYPE);

        // Get all the imProjectsList where risktype equals to UPDATED_RISKTYPE
        defaultImProjectsShouldNotBeFound("risktype.equals=" + UPDATED_RISKTYPE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByRisktypeIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where risktype in DEFAULT_RISKTYPE or UPDATED_RISKTYPE
        defaultImProjectsShouldBeFound("risktype.in=" + DEFAULT_RISKTYPE + "," + UPDATED_RISKTYPE);

        // Get all the imProjectsList where risktype equals to UPDATED_RISKTYPE
        defaultImProjectsShouldNotBeFound("risktype.in=" + UPDATED_RISKTYPE);
    }

    @Test
    @Transactional
    public void getAllImProjectsByRisktypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where risktype is not null
        defaultImProjectsShouldBeFound("risktype.specified=true");

        // Get all the imProjectsList where risktype is null
        defaultImProjectsShouldNotBeFound("risktype.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByRiskimpactIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where riskimpact equals to DEFAULT_RISKIMPACT
        defaultImProjectsShouldBeFound("riskimpact.equals=" + DEFAULT_RISKIMPACT);

        // Get all the imProjectsList where riskimpact equals to UPDATED_RISKIMPACT
        defaultImProjectsShouldNotBeFound("riskimpact.equals=" + UPDATED_RISKIMPACT);
    }

    @Test
    @Transactional
    public void getAllImProjectsByRiskimpactIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where riskimpact in DEFAULT_RISKIMPACT or UPDATED_RISKIMPACT
        defaultImProjectsShouldBeFound("riskimpact.in=" + DEFAULT_RISKIMPACT + "," + UPDATED_RISKIMPACT);

        // Get all the imProjectsList where riskimpact equals to UPDATED_RISKIMPACT
        defaultImProjectsShouldNotBeFound("riskimpact.in=" + UPDATED_RISKIMPACT);
    }

    @Test
    @Transactional
    public void getAllImProjectsByRiskimpactIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where riskimpact is not null
        defaultImProjectsShouldBeFound("riskimpact.specified=true");

        // Get all the imProjectsList where riskimpact is null
        defaultImProjectsShouldNotBeFound("riskimpact.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByRiskprobabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where riskprobability equals to DEFAULT_RISKPROBABILITY
        defaultImProjectsShouldBeFound("riskprobability.equals=" + DEFAULT_RISKPROBABILITY);

        // Get all the imProjectsList where riskprobability equals to UPDATED_RISKPROBABILITY
        defaultImProjectsShouldNotBeFound("riskprobability.equals=" + UPDATED_RISKPROBABILITY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByRiskprobabilityIsInShouldWork() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where riskprobability in DEFAULT_RISKPROBABILITY or UPDATED_RISKPROBABILITY
        defaultImProjectsShouldBeFound("riskprobability.in=" + DEFAULT_RISKPROBABILITY + "," + UPDATED_RISKPROBABILITY);

        // Get all the imProjectsList where riskprobability equals to UPDATED_RISKPROBABILITY
        defaultImProjectsShouldNotBeFound("riskprobability.in=" + UPDATED_RISKPROBABILITY);
    }

    @Test
    @Transactional
    public void getAllImProjectsByRiskprobabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        imProjectsRepository.saveAndFlush(imProjects);

        // Get all the imProjectsList where riskprobability is not null
        defaultImProjectsShouldBeFound("riskprobability.specified=true");

        // Get all the imProjectsList where riskprobability is null
        defaultImProjectsShouldNotBeFound("riskprobability.specified=false");
    }

    @Test
    @Transactional
    public void getAllImProjectsByProjectInitiativeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectInitiativeId projectInitiativeId = ProjectInitiativeIdResourceIT.createEntity(em);
        em.persist(projectInitiativeId);
        em.flush();
        imProjects.setProjectInitiativeId(projectInitiativeId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectInitiativeIdId = projectInitiativeId.getId();

        // Get all the imProjectsList where projectInitiativeId equals to projectInitiativeIdId
        defaultImProjectsShouldBeFound("projectInitiativeIdId.equals=" + projectInitiativeIdId);

        // Get all the imProjectsList where projectInitiativeId equals to projectInitiativeIdId + 1
        defaultImProjectsShouldNotBeFound("projectInitiativeIdId.equals=" + (projectInitiativeIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectBusinessgoalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectBusinessgoalId projectBusinessgoalId = ProjectBusinessgoalIdResourceIT.createEntity(em);
        em.persist(projectBusinessgoalId);
        em.flush();
        imProjects.setProjectBusinessgoalId(projectBusinessgoalId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectBusinessgoalIdId = projectBusinessgoalId.getId();

        // Get all the imProjectsList where projectBusinessgoalId equals to projectBusinessgoalIdId
        defaultImProjectsShouldBeFound("projectBusinessgoalIdId.equals=" + projectBusinessgoalIdId);

        // Get all the imProjectsList where projectBusinessgoalId equals to projectBusinessgoalIdId + 1
        defaultImProjectsShouldNotBeFound("projectBusinessgoalIdId.equals=" + (projectBusinessgoalIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectSubgoalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectSubgoalId projectSubgoalId = ProjectSubgoalIdResourceIT.createEntity(em);
        em.persist(projectSubgoalId);
        em.flush();
        imProjects.setProjectSubgoalId(projectSubgoalId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectSubgoalIdId = projectSubgoalId.getId();

        // Get all the imProjectsList where projectSubgoalId equals to projectSubgoalIdId
        defaultImProjectsShouldBeFound("projectSubgoalIdId.equals=" + projectSubgoalIdId);

        // Get all the imProjectsList where projectSubgoalId equals to projectSubgoalIdId + 1
        defaultImProjectsShouldNotBeFound("projectSubgoalIdId.equals=" + (projectSubgoalIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectMaingoalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectMaingoalId projectMaingoalId = ProjectMaingoalIdResourceIT.createEntity(em);
        em.persist(projectMaingoalId);
        em.flush();
        imProjects.setProjectMaingoalId(projectMaingoalId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectMaingoalIdId = projectMaingoalId.getId();

        // Get all the imProjectsList where projectMaingoalId equals to projectMaingoalIdId
        defaultImProjectsShouldBeFound("projectMaingoalIdId.equals=" + projectMaingoalIdId);

        // Get all the imProjectsList where projectMaingoalId equals to projectMaingoalIdId + 1
        defaultImProjectsShouldNotBeFound("projectMaingoalIdId.equals=" + (projectMaingoalIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectBucketIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectBucketId projectBucketId = ProjectBucketIdResourceIT.createEntity(em);
        em.persist(projectBucketId);
        em.flush();
        imProjects.setProjectBucketId(projectBucketId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectBucketIdId = projectBucketId.getId();

        // Get all the imProjectsList where projectBucketId equals to projectBucketIdId
        defaultImProjectsShouldBeFound("projectBucketIdId.equals=" + projectBucketIdId);

        // Get all the imProjectsList where projectBucketId equals to projectBucketIdId + 1
        defaultImProjectsShouldNotBeFound("projectBucketIdId.equals=" + (projectBucketIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectCostCenterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectCostCenterId projectCostCenterId = ProjectCostCenterIdResourceIT.createEntity(em);
        em.persist(projectCostCenterId);
        em.flush();
        imProjects.setProjectCostCenterId(projectCostCenterId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectCostCenterIdId = projectCostCenterId.getId();

        // Get all the imProjectsList where projectCostCenterId equals to projectCostCenterIdId
        defaultImProjectsShouldBeFound("projectCostCenterIdId.equals=" + projectCostCenterIdId);

        // Get all the imProjectsList where projectCostCenterId equals to projectCostCenterIdId + 1
        defaultImProjectsShouldNotBeFound("projectCostCenterIdId.equals=" + (projectCostCenterIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByOpportunityPriorityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        OpportunityPriorityId opportunityPriorityId = OpportunityPriorityIdResourceIT.createEntity(em);
        em.persist(opportunityPriorityId);
        em.flush();
        imProjects.setOpportunityPriorityId(opportunityPriorityId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long opportunityPriorityIdId = opportunityPriorityId.getId();

        // Get all the imProjectsList where opportunityPriorityId equals to opportunityPriorityIdId
        defaultImProjectsShouldBeFound("opportunityPriorityIdId.equals=" + opportunityPriorityIdId);

        // Get all the imProjectsList where opportunityPriorityId equals to opportunityPriorityIdId + 1
        defaultImProjectsShouldNotBeFound("opportunityPriorityIdId.equals=" + (opportunityPriorityIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByBacklogPracticeIsEqualToSomething() throws Exception {
        // Initialize the database
        BacklogPractice backlogPractice = BacklogPracticeResourceIT.createEntity(em);
        em.persist(backlogPractice);
        em.flush();
        imProjects.setBacklogPractice(backlogPractice);
        imProjectsRepository.saveAndFlush(imProjects);
        Long backlogPracticeId = backlogPractice.getId();

        // Get all the imProjectsList where backlogPractice equals to backlogPracticeId
        defaultImProjectsShouldBeFound("backlogPracticeId.equals=" + backlogPracticeId);

        // Get all the imProjectsList where backlogPractice equals to backlogPracticeId + 1
        defaultImProjectsShouldNotBeFound("backlogPracticeId.equals=" + (backlogPracticeId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectThemeIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectTheme projectTheme = ProjectThemeResourceIT.createEntity(em);
        em.persist(projectTheme);
        em.flush();
        imProjects.setProjectTheme(projectTheme);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectThemeId = projectTheme.getId();

        // Get all the imProjectsList where projectTheme equals to projectThemeId
        defaultImProjectsShouldBeFound("projectThemeId.equals=" + projectThemeId);

        // Get all the imProjectsList where projectTheme equals to projectThemeId + 1
        defaultImProjectsShouldNotBeFound("projectThemeId.equals=" + (projectThemeId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectClassIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectClass projectClass = ProjectClassResourceIT.createEntity(em);
        em.persist(projectClass);
        em.flush();
        imProjects.setProjectClass(projectClass);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectClassId = projectClass.getId();

        // Get all the imProjectsList where projectClass equals to projectClassId
        defaultImProjectsShouldBeFound("projectClassId.equals=" + projectClassId);

        // Get all the imProjectsList where projectClass equals to projectClassId + 1
        defaultImProjectsShouldNotBeFound("projectClassId.equals=" + (projectClassId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectVerticalIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectVertical projectVertical = ProjectVerticalResourceIT.createEntity(em);
        em.persist(projectVertical);
        em.flush();
        imProjects.setProjectVertical(projectVertical);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectVerticalId = projectVertical.getId();

        // Get all the imProjectsList where projectVertical equals to projectVerticalId
        defaultImProjectsShouldBeFound("projectVerticalId.equals=" + projectVerticalId);

        // Get all the imProjectsList where projectVertical equals to projectVerticalId + 1
        defaultImProjectsShouldNotBeFound("projectVerticalId.equals=" + (projectVerticalId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectBoardIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectBoardId projectBoardId = ProjectBoardIdResourceIT.createEntity(em);
        em.persist(projectBoardId);
        em.flush();
        imProjects.setProjectBoardId(projectBoardId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectBoardIdId = projectBoardId.getId();

        // Get all the imProjectsList where projectBoardId equals to projectBoardIdId
        defaultImProjectsShouldBeFound("projectBoardIdId.equals=" + projectBoardIdId);

        // Get all the imProjectsList where projectBoardId equals to projectBoardIdId + 1
        defaultImProjectsShouldNotBeFound("projectBoardIdId.equals=" + (projectBoardIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectBoard2IdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectBoardId projectBoard2Id = ProjectBoardIdResourceIT.createEntity(em);
        em.persist(projectBoard2Id);
        em.flush();
        imProjects.setProjectBoard2Id(projectBoard2Id);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectBoard2IdId = projectBoard2Id.getId();

        // Get all the imProjectsList where projectBoard2Id equals to projectBoard2IdId
        defaultImProjectsShouldBeFound("projectBoard2IdId.equals=" + projectBoard2IdId);

        // Get all the imProjectsList where projectBoard2Id equals to projectBoard2IdId + 1
        defaultImProjectsShouldNotBeFound("projectBoard2IdId.equals=" + (projectBoard2IdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectStatusIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectStatusId projectStatusId = ProjectStatusIdResourceIT.createEntity(em);
        em.persist(projectStatusId);
        em.flush();
        imProjects.setProjectStatusId(projectStatusId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectStatusIdId = projectStatusId.getId();

        // Get all the imProjectsList where projectStatusId equals to projectStatusIdId
        defaultImProjectsShouldBeFound("projectStatusIdId.equals=" + projectStatusIdId);

        // Get all the imProjectsList where projectStatusId equals to projectStatusIdId + 1
        defaultImProjectsShouldNotBeFound("projectStatusIdId.equals=" + (projectStatusIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectTypeId projectTypeId = ProjectTypeIdResourceIT.createEntity(em);
        em.persist(projectTypeId);
        em.flush();
        imProjects.setProjectTypeId(projectTypeId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectTypeIdId = projectTypeId.getId();

        // Get all the imProjectsList where projectTypeId equals to projectTypeIdId
        defaultImProjectsShouldBeFound("projectTypeIdId.equals=" + projectTypeIdId);

        // Get all the imProjectsList where projectTypeId equals to projectTypeIdId + 1
        defaultImProjectsShouldNotBeFound("projectTypeIdId.equals=" + (projectTypeIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByProjectLeadIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ImEmployee projectLeadId = ImEmployeeResourceIT.createEntity(em);
        em.persist(projectLeadId);
        em.flush();
        imProjects.setProjectLeadId(projectLeadId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long projectLeadIdId = projectLeadId.getId();

        // Get all the imProjectsList where projectLeadId equals to projectLeadIdId
        defaultImProjectsShouldBeFound("projectLeadIdId.equals=" + projectLeadIdId);

        // Get all the imProjectsList where projectLeadId equals to projectLeadIdId + 1
        defaultImProjectsShouldNotBeFound("projectLeadIdId.equals=" + (projectLeadIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImProjectsByParentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ImProjects parentId = ImProjectsResourceIT.createEntity(em);
        em.persist(parentId);
        em.flush();
        imProjects.setParentId(parentId);
        imProjectsRepository.saveAndFlush(imProjects);
        Long parentIdId = parentId.getId();

        // Get all the imProjectsList where parentId equals to parentIdId
        defaultImProjectsShouldBeFound("parentIdId.equals=" + parentIdId);

        // Get all the imProjectsList where parentId equals to parentIdId + 1
        defaultImProjectsShouldNotBeFound("parentIdId.equals=" + (parentIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImProjectsShouldBeFound(String filter) throws Exception {
        restImProjectsMockMvc.perform(get("/api/im-projects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imProjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].projectNr").value(hasItem(DEFAULT_PROJECT_NR)))
            .andExpect(jsonPath("$.[*].projectPath").value(hasItem(DEFAULT_PROJECT_PATH)))
            .andExpect(jsonPath("$.[*].treeSortkey").value(hasItem(DEFAULT_TREE_SORTKEY)))
            .andExpect(jsonPath("$.[*].maxChildSortkey").value(hasItem(DEFAULT_MAX_CHILD_SORTKEY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].billingTypeId").value(hasItem(DEFAULT_BILLING_TYPE_ID)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].requiresReportP").value(hasItem(DEFAULT_REQUIRES_REPORT_P.booleanValue())))
            .andExpect(jsonPath("$.[*].projectBudget").value(hasItem(DEFAULT_PROJECT_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].projectRisk").value(hasItem(DEFAULT_PROJECT_RISK)))
            .andExpect(jsonPath("$.[*].corporateSponsor").value(hasItem(DEFAULT_CORPORATE_SPONSOR)))
            .andExpect(jsonPath("$.[*].percentCompleted").value(hasItem(DEFAULT_PERCENT_COMPLETED.doubleValue())))
            .andExpect(jsonPath("$.[*].projectBudgetHours").value(hasItem(DEFAULT_PROJECT_BUDGET_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].costQuotesCache").value(hasItem(DEFAULT_COST_QUOTES_CACHE.intValue())))
            .andExpect(jsonPath("$.[*].costInvoicesCache").value(hasItem(DEFAULT_COST_INVOICES_CACHE)))
            .andExpect(jsonPath("$.[*].costTimesheetPlannedCache").value(hasItem(DEFAULT_COST_TIMESHEET_PLANNED_CACHE)))
            .andExpect(jsonPath("$.[*].costPurchaseOrdersCache").value(hasItem(DEFAULT_COST_PURCHASE_ORDERS_CACHE)))
            .andExpect(jsonPath("$.[*].costBillsCache").value(hasItem(DEFAULT_COST_BILLS_CACHE)))
            .andExpect(jsonPath("$.[*].costTimesheetLoggedCache").value(hasItem(DEFAULT_COST_TIMESHEET_LOGGED_CACHE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].templateP").value(hasItem(DEFAULT_TEMPLATE_P.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].reportedHoursCache").value(hasItem(DEFAULT_REPORTED_HOURS_CACHE.doubleValue())))
            .andExpect(jsonPath("$.[*].costExpensePlannedCache").value(hasItem(DEFAULT_COST_EXPENSE_PLANNED_CACHE)))
            .andExpect(jsonPath("$.[*].costExpenseLoggedCache").value(hasItem(DEFAULT_COST_EXPENSE_LOGGED_CACHE)))
            .andExpect(jsonPath("$.[*].confirmDate").value(hasItem(DEFAULT_CONFIRM_DATE.toString())))
            .andExpect(jsonPath("$.[*].costDeliveryNotesCache").value(hasItem(DEFAULT_COST_DELIVERY_NOTES_CACHE.intValue())))
            .andExpect(jsonPath("$.[*].costCacheDirty").value(hasItem(sameInstant(DEFAULT_COST_CACHE_DIRTY))))
            .andExpect(jsonPath("$.[*].milestoneP").value(hasItem(DEFAULT_MILESTONE_P.booleanValue())))
            .andExpect(jsonPath("$.[*].releaseItemP").value(hasItem(DEFAULT_RELEASE_ITEM_P)))
            .andExpect(jsonPath("$.[*].presalesProbability").value(hasItem(DEFAULT_PRESALES_PROBABILITY.intValue())))
            .andExpect(jsonPath("$.[*].presalesValue").value(hasItem(DEFAULT_PRESALES_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].reportedDaysCache").value(hasItem(DEFAULT_REPORTED_DAYS_CACHE.intValue())))
            .andExpect(jsonPath("$.[*].presalesValueCurrency").value(hasItem(DEFAULT_PRESALES_VALUE_CURRENCY)))
            .andExpect(jsonPath("$.[*].opportunitySalesStageId").value(hasItem(DEFAULT_OPPORTUNITY_SALES_STAGE_ID)))
            .andExpect(jsonPath("$.[*].opportunityCampaignId").value(hasItem(DEFAULT_OPPORTUNITY_CAMPAIGN_ID)))
            .andExpect(jsonPath("$.[*].scoreRevenue").value(hasItem(DEFAULT_SCORE_REVENUE.intValue())))
            .andExpect(jsonPath("$.[*].scoreStrategic").value(hasItem(DEFAULT_SCORE_STRATEGIC.intValue())))
            .andExpect(jsonPath("$.[*].scoreFinanceNpv").value(hasItem(DEFAULT_SCORE_FINANCE_NPV.intValue())))
            .andExpect(jsonPath("$.[*].scoreCustomers").value(hasItem(DEFAULT_SCORE_CUSTOMERS.intValue())))
            .andExpect(jsonPath("$.[*].scoreFinanceCost").value(hasItem(DEFAULT_SCORE_FINANCE_COST.intValue())))
            .andExpect(jsonPath("$.[*].costBillsPlanned").value(hasItem(DEFAULT_COST_BILLS_PLANNED.intValue())))
            .andExpect(jsonPath("$.[*].costExpensesPlanned").value(hasItem(DEFAULT_COST_EXPENSES_PLANNED.intValue())))
            .andExpect(jsonPath("$.[*].scoreRisk").value(hasItem(DEFAULT_SCORE_RISK.intValue())))
            .andExpect(jsonPath("$.[*].scoreCapabilities").value(hasItem(DEFAULT_SCORE_CAPABILITIES.intValue())))
            .andExpect(jsonPath("$.[*].scoreEinanceRoi").value(hasItem(DEFAULT_SCORE_EINANCE_ROI.intValue())))
            .andExpect(jsonPath("$.[*].projectUserwiseBoard").value(hasItem(DEFAULT_PROJECT_USERWISE_BOARD)))
            .andExpect(jsonPath("$.[*].projectBringNextday").value(hasItem(DEFAULT_PROJECT_BRING_NEXTDAY)))
            .andExpect(jsonPath("$.[*].projectBringSameboard").value(hasItem(DEFAULT_PROJECT_BRING_SAMEBOARD)))
            .andExpect(jsonPath("$.[*].projectNewboardEverytime").value(hasItem(DEFAULT_PROJECT_NEWBOARD_EVERYTIME)))
            .andExpect(jsonPath("$.[*].projectUserwiseBoard2").value(hasItem(DEFAULT_PROJECT_USERWISE_BOARD_2)))
            .andExpect(jsonPath("$.[*].projectBringSameboard2").value(hasItem(DEFAULT_PROJECT_BRING_SAMEBOARD_2)))
            .andExpect(jsonPath("$.[*].projectNewboard2Everytime").value(hasItem(DEFAULT_PROJECT_NEWBOARD_2_EVERYTIME)))
            .andExpect(jsonPath("$.[*].projectNewboard2Always").value(hasItem(DEFAULT_PROJECT_NEWBOARD_2_ALWAYS)))
            .andExpect(jsonPath("$.[*].projectReportWeekly").value(hasItem(DEFAULT_PROJECT_REPORT_WEEKLY)))
            .andExpect(jsonPath("$.[*].scoreGain").value(hasItem(DEFAULT_SCORE_GAIN.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreLoss").value(hasItem(DEFAULT_SCORE_LOSS.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreDelivery").value(hasItem(DEFAULT_SCORE_DELIVERY.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreOperations").value(hasItem(DEFAULT_SCORE_OPERATIONS.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreWhy").value(hasItem(DEFAULT_SCORE_WHY)))
            .andExpect(jsonPath("$.[*].javaServices").value(hasItem(DEFAULT_JAVA_SERVICES)))
            .andExpect(jsonPath("$.[*].netServices").value(hasItem(DEFAULT_NET_SERVICES)))
            .andExpect(jsonPath("$.[*].collectionLink").value(hasItem(DEFAULT_COLLECTION_LINK)))
            .andExpect(jsonPath("$.[*].trainingLink").value(hasItem(DEFAULT_TRAINING_LINK)))
            .andExpect(jsonPath("$.[*].collectionName").value(hasItem(DEFAULT_COLLECTION_NAME)))
            .andExpect(jsonPath("$.[*].trainingName").value(hasItem(DEFAULT_TRAINING_NAME)))
            .andExpect(jsonPath("$.[*].trainingDoc").value(hasItem(DEFAULT_TRAINING_DOC)))
            .andExpect(jsonPath("$.[*].testingRichtext").value(hasItem(DEFAULT_TESTING_RICHTEXT)))
            .andExpect(jsonPath("$.[*].templateCategory").value(hasItem(DEFAULT_TEMPLATE_CATEGORY)))
            .andExpect(jsonPath("$.[*].dType").value(hasItem(DEFAULT_D_TYPE)))
            .andExpect(jsonPath("$.[*].dOption").value(hasItem(DEFAULT_D_OPTION)))
            .andExpect(jsonPath("$.[*].dFilter").value(hasItem(DEFAULT_D_FILTER)))
            .andExpect(jsonPath("$.[*].dId").value(hasItem(DEFAULT_D_ID)))
            .andExpect(jsonPath("$.[*].tType").value(hasItem(DEFAULT_T_TYPE)))
            .andExpect(jsonPath("$.[*].tOption").value(hasItem(DEFAULT_T_OPTION)))
            .andExpect(jsonPath("$.[*].tFilter").value(hasItem(DEFAULT_T_FILTER)))
            .andExpect(jsonPath("$.[*].tId").value(hasItem(DEFAULT_T_ID)))
            .andExpect(jsonPath("$.[*].risktype").value(hasItem(DEFAULT_RISKTYPE)))
            .andExpect(jsonPath("$.[*].riskimpact").value(hasItem(DEFAULT_RISKIMPACT.doubleValue())))
            .andExpect(jsonPath("$.[*].riskprobability").value(hasItem(DEFAULT_RISKPROBABILITY.doubleValue())));

        // Check, that the count call also returns 1
        restImProjectsMockMvc.perform(get("/api/im-projects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImProjectsShouldNotBeFound(String filter) throws Exception {
        restImProjectsMockMvc.perform(get("/api/im-projects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImProjectsMockMvc.perform(get("/api/im-projects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingImProjects() throws Exception {
        // Get the imProjects
        restImProjectsMockMvc.perform(get("/api/im-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImProjects() throws Exception {
        // Initialize the database
        imProjectsService.save(imProjects);

        int databaseSizeBeforeUpdate = imProjectsRepository.findAll().size();

        // Update the imProjects
        ImProjects updatedImProjects = imProjectsRepository.findById(imProjects.getId()).get();
        // Disconnect from session so that the updates on updatedImProjects are not directly saved in db
        em.detach(updatedImProjects);
        updatedImProjects
            .projectName(UPDATED_PROJECT_NAME)
            .projectNr(UPDATED_PROJECT_NR)
            .projectPath(UPDATED_PROJECT_PATH)
            .treeSortkey(UPDATED_TREE_SORTKEY)
            .maxChildSortkey(UPDATED_MAX_CHILD_SORTKEY)
            .description(UPDATED_DESCRIPTION)
            .billingTypeId(UPDATED_BILLING_TYPE_ID)
            .note(UPDATED_NOTE)
            .requiresReportP(UPDATED_REQUIRES_REPORT_P)
            .projectBudget(UPDATED_PROJECT_BUDGET)
            .projectRisk(UPDATED_PROJECT_RISK)
            .corporateSponsor(UPDATED_CORPORATE_SPONSOR)
            .percentCompleted(UPDATED_PERCENT_COMPLETED)
            .projectBudgetHours(UPDATED_PROJECT_BUDGET_HOURS)
            .costQuotesCache(UPDATED_COST_QUOTES_CACHE)
            .costInvoicesCache(UPDATED_COST_INVOICES_CACHE)
            .costTimesheetPlannedCache(UPDATED_COST_TIMESHEET_PLANNED_CACHE)
            .costPurchaseOrdersCache(UPDATED_COST_PURCHASE_ORDERS_CACHE)
            .costBillsCache(UPDATED_COST_BILLS_CACHE)
            .costTimesheetLoggedCache(UPDATED_COST_TIMESHEET_LOGGED_CACHE)
            .endDate(UPDATED_END_DATE)
            .startDate(UPDATED_START_DATE)
            .templateP(UPDATED_TEMPLATE_P)
            .sortOrder(UPDATED_SORT_ORDER)
            .reportedHoursCache(UPDATED_REPORTED_HOURS_CACHE)
            .costExpensePlannedCache(UPDATED_COST_EXPENSE_PLANNED_CACHE)
            .costExpenseLoggedCache(UPDATED_COST_EXPENSE_LOGGED_CACHE)
            .confirmDate(UPDATED_CONFIRM_DATE)
            .costDeliveryNotesCache(UPDATED_COST_DELIVERY_NOTES_CACHE)
            .costCacheDirty(UPDATED_COST_CACHE_DIRTY)
            .milestoneP(UPDATED_MILESTONE_P)
            .releaseItemP(UPDATED_RELEASE_ITEM_P)
            .presalesProbability(UPDATED_PRESALES_PROBABILITY)
            .presalesValue(UPDATED_PRESALES_VALUE)
            .reportedDaysCache(UPDATED_REPORTED_DAYS_CACHE)
            .presalesValueCurrency(UPDATED_PRESALES_VALUE_CURRENCY)
            .opportunitySalesStageId(UPDATED_OPPORTUNITY_SALES_STAGE_ID)
            .opportunityCampaignId(UPDATED_OPPORTUNITY_CAMPAIGN_ID)
            .scoreRevenue(UPDATED_SCORE_REVENUE)
            .scoreStrategic(UPDATED_SCORE_STRATEGIC)
            .scoreFinanceNpv(UPDATED_SCORE_FINANCE_NPV)
            .scoreCustomers(UPDATED_SCORE_CUSTOMERS)
            .scoreFinanceCost(UPDATED_SCORE_FINANCE_COST)
            .costBillsPlanned(UPDATED_COST_BILLS_PLANNED)
            .costExpensesPlanned(UPDATED_COST_EXPENSES_PLANNED)
            .scoreRisk(UPDATED_SCORE_RISK)
            .scoreCapabilities(UPDATED_SCORE_CAPABILITIES)
            .scoreEinanceRoi(UPDATED_SCORE_EINANCE_ROI)
            .projectUserwiseBoard(UPDATED_PROJECT_USERWISE_BOARD)
            .projectBringNextday(UPDATED_PROJECT_BRING_NEXTDAY)
            .projectBringSameboard(UPDATED_PROJECT_BRING_SAMEBOARD)
            .projectNewboardEverytime(UPDATED_PROJECT_NEWBOARD_EVERYTIME)
            .projectUserwiseBoard2(UPDATED_PROJECT_USERWISE_BOARD_2)
            .projectBringSameboard2(UPDATED_PROJECT_BRING_SAMEBOARD_2)
            .projectNewboard2Everytime(UPDATED_PROJECT_NEWBOARD_2_EVERYTIME)
            .projectNewboard2Always(UPDATED_PROJECT_NEWBOARD_2_ALWAYS)
            .projectReportWeekly(UPDATED_PROJECT_REPORT_WEEKLY)
            .scoreGain(UPDATED_SCORE_GAIN)
            .scoreLoss(UPDATED_SCORE_LOSS)
            .scoreDelivery(UPDATED_SCORE_DELIVERY)
            .scoreOperations(UPDATED_SCORE_OPERATIONS)
            .scoreWhy(UPDATED_SCORE_WHY)
            .javaServices(UPDATED_JAVA_SERVICES)
            .netServices(UPDATED_NET_SERVICES)
            .collectionLink(UPDATED_COLLECTION_LINK)
            .trainingLink(UPDATED_TRAINING_LINK)
            .collectionName(UPDATED_COLLECTION_NAME)
            .trainingName(UPDATED_TRAINING_NAME)
            .trainingDoc(UPDATED_TRAINING_DOC)
            .testingRichtext(UPDATED_TESTING_RICHTEXT)
            .templateCategory(UPDATED_TEMPLATE_CATEGORY)
            .dType(UPDATED_D_TYPE)
            .dOption(UPDATED_D_OPTION)
            .dFilter(UPDATED_D_FILTER)
            .dId(UPDATED_D_ID)
            .tType(UPDATED_T_TYPE)
            .tOption(UPDATED_T_OPTION)
            .tFilter(UPDATED_T_FILTER)
            .tId(UPDATED_T_ID)
            .risktype(UPDATED_RISKTYPE)
            .riskimpact(UPDATED_RISKIMPACT)
            .riskprobability(UPDATED_RISKPROBABILITY);

        restImProjectsMockMvc.perform(put("/api/im-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImProjects)))
            .andExpect(status().isOk());

        // Validate the ImProjects in the database
        List<ImProjects> imProjectsList = imProjectsRepository.findAll();
        assertThat(imProjectsList).hasSize(databaseSizeBeforeUpdate);
        ImProjects testImProjects = imProjectsList.get(imProjectsList.size() - 1);
        assertThat(testImProjects.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testImProjects.getProjectNr()).isEqualTo(UPDATED_PROJECT_NR);
        assertThat(testImProjects.getProjectPath()).isEqualTo(UPDATED_PROJECT_PATH);
        assertThat(testImProjects.getTreeSortkey()).isEqualTo(UPDATED_TREE_SORTKEY);
        assertThat(testImProjects.getMaxChildSortkey()).isEqualTo(UPDATED_MAX_CHILD_SORTKEY);
        assertThat(testImProjects.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testImProjects.getBillingTypeId()).isEqualTo(UPDATED_BILLING_TYPE_ID);
        assertThat(testImProjects.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testImProjects.isRequiresReportP()).isEqualTo(UPDATED_REQUIRES_REPORT_P);
        assertThat(testImProjects.getProjectBudget()).isEqualTo(UPDATED_PROJECT_BUDGET);
        assertThat(testImProjects.getProjectRisk()).isEqualTo(UPDATED_PROJECT_RISK);
        assertThat(testImProjects.getCorporateSponsor()).isEqualTo(UPDATED_CORPORATE_SPONSOR);
        assertThat(testImProjects.getPercentCompleted()).isEqualTo(UPDATED_PERCENT_COMPLETED);
        assertThat(testImProjects.getProjectBudgetHours()).isEqualTo(UPDATED_PROJECT_BUDGET_HOURS);
        assertThat(testImProjects.getCostQuotesCache()).isEqualTo(UPDATED_COST_QUOTES_CACHE);
        assertThat(testImProjects.getCostInvoicesCache()).isEqualTo(UPDATED_COST_INVOICES_CACHE);
        assertThat(testImProjects.getCostTimesheetPlannedCache()).isEqualTo(UPDATED_COST_TIMESHEET_PLANNED_CACHE);
        assertThat(testImProjects.getCostPurchaseOrdersCache()).isEqualTo(UPDATED_COST_PURCHASE_ORDERS_CACHE);
        assertThat(testImProjects.getCostBillsCache()).isEqualTo(UPDATED_COST_BILLS_CACHE);
        assertThat(testImProjects.getCostTimesheetLoggedCache()).isEqualTo(UPDATED_COST_TIMESHEET_LOGGED_CACHE);
        assertThat(testImProjects.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testImProjects.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testImProjects.isTemplateP()).isEqualTo(UPDATED_TEMPLATE_P);
        assertThat(testImProjects.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testImProjects.getReportedHoursCache()).isEqualTo(UPDATED_REPORTED_HOURS_CACHE);
        assertThat(testImProjects.getCostExpensePlannedCache()).isEqualTo(UPDATED_COST_EXPENSE_PLANNED_CACHE);
        assertThat(testImProjects.getCostExpenseLoggedCache()).isEqualTo(UPDATED_COST_EXPENSE_LOGGED_CACHE);
        assertThat(testImProjects.getConfirmDate()).isEqualTo(UPDATED_CONFIRM_DATE);
        assertThat(testImProjects.getCostDeliveryNotesCache()).isEqualTo(UPDATED_COST_DELIVERY_NOTES_CACHE);
        assertThat(testImProjects.getCostCacheDirty()).isEqualTo(UPDATED_COST_CACHE_DIRTY);
        assertThat(testImProjects.isMilestoneP()).isEqualTo(UPDATED_MILESTONE_P);
        assertThat(testImProjects.getReleaseItemP()).isEqualTo(UPDATED_RELEASE_ITEM_P);
        assertThat(testImProjects.getPresalesProbability()).isEqualTo(UPDATED_PRESALES_PROBABILITY);
        assertThat(testImProjects.getPresalesValue()).isEqualTo(UPDATED_PRESALES_VALUE);
        assertThat(testImProjects.getReportedDaysCache()).isEqualTo(UPDATED_REPORTED_DAYS_CACHE);
        assertThat(testImProjects.getPresalesValueCurrency()).isEqualTo(UPDATED_PRESALES_VALUE_CURRENCY);
        assertThat(testImProjects.getOpportunitySalesStageId()).isEqualTo(UPDATED_OPPORTUNITY_SALES_STAGE_ID);
        assertThat(testImProjects.getOpportunityCampaignId()).isEqualTo(UPDATED_OPPORTUNITY_CAMPAIGN_ID);
        assertThat(testImProjects.getScoreRevenue()).isEqualTo(UPDATED_SCORE_REVENUE);
        assertThat(testImProjects.getScoreStrategic()).isEqualTo(UPDATED_SCORE_STRATEGIC);
        assertThat(testImProjects.getScoreFinanceNpv()).isEqualTo(UPDATED_SCORE_FINANCE_NPV);
        assertThat(testImProjects.getScoreCustomers()).isEqualTo(UPDATED_SCORE_CUSTOMERS);
        assertThat(testImProjects.getScoreFinanceCost()).isEqualTo(UPDATED_SCORE_FINANCE_COST);
        assertThat(testImProjects.getCostBillsPlanned()).isEqualTo(UPDATED_COST_BILLS_PLANNED);
        assertThat(testImProjects.getCostExpensesPlanned()).isEqualTo(UPDATED_COST_EXPENSES_PLANNED);
        assertThat(testImProjects.getScoreRisk()).isEqualTo(UPDATED_SCORE_RISK);
        assertThat(testImProjects.getScoreCapabilities()).isEqualTo(UPDATED_SCORE_CAPABILITIES);
        assertThat(testImProjects.getScoreEinanceRoi()).isEqualTo(UPDATED_SCORE_EINANCE_ROI);
        assertThat(testImProjects.getProjectUserwiseBoard()).isEqualTo(UPDATED_PROJECT_USERWISE_BOARD);
        assertThat(testImProjects.getProjectBringNextday()).isEqualTo(UPDATED_PROJECT_BRING_NEXTDAY);
        assertThat(testImProjects.getProjectBringSameboard()).isEqualTo(UPDATED_PROJECT_BRING_SAMEBOARD);
        assertThat(testImProjects.getProjectNewboardEverytime()).isEqualTo(UPDATED_PROJECT_NEWBOARD_EVERYTIME);
        assertThat(testImProjects.getProjectUserwiseBoard2()).isEqualTo(UPDATED_PROJECT_USERWISE_BOARD_2);
        assertThat(testImProjects.getProjectBringSameboard2()).isEqualTo(UPDATED_PROJECT_BRING_SAMEBOARD_2);
        assertThat(testImProjects.getProjectNewboard2Everytime()).isEqualTo(UPDATED_PROJECT_NEWBOARD_2_EVERYTIME);
        assertThat(testImProjects.getProjectNewboard2Always()).isEqualTo(UPDATED_PROJECT_NEWBOARD_2_ALWAYS);
        assertThat(testImProjects.getProjectReportWeekly()).isEqualTo(UPDATED_PROJECT_REPORT_WEEKLY);
        assertThat(testImProjects.getScoreGain()).isEqualTo(UPDATED_SCORE_GAIN);
        assertThat(testImProjects.getScoreLoss()).isEqualTo(UPDATED_SCORE_LOSS);
        assertThat(testImProjects.getScoreDelivery()).isEqualTo(UPDATED_SCORE_DELIVERY);
        assertThat(testImProjects.getScoreOperations()).isEqualTo(UPDATED_SCORE_OPERATIONS);
        assertThat(testImProjects.getScoreWhy()).isEqualTo(UPDATED_SCORE_WHY);
        assertThat(testImProjects.getJavaServices()).isEqualTo(UPDATED_JAVA_SERVICES);
        assertThat(testImProjects.getNetServices()).isEqualTo(UPDATED_NET_SERVICES);
        assertThat(testImProjects.getCollectionLink()).isEqualTo(UPDATED_COLLECTION_LINK);
        assertThat(testImProjects.getTrainingLink()).isEqualTo(UPDATED_TRAINING_LINK);
        assertThat(testImProjects.getCollectionName()).isEqualTo(UPDATED_COLLECTION_NAME);
        assertThat(testImProjects.getTrainingName()).isEqualTo(UPDATED_TRAINING_NAME);
        assertThat(testImProjects.getTrainingDoc()).isEqualTo(UPDATED_TRAINING_DOC);
        assertThat(testImProjects.getTestingRichtext()).isEqualTo(UPDATED_TESTING_RICHTEXT);
        assertThat(testImProjects.getTemplateCategory()).isEqualTo(UPDATED_TEMPLATE_CATEGORY);
        assertThat(testImProjects.getdType()).isEqualTo(UPDATED_D_TYPE);
        assertThat(testImProjects.getdOption()).isEqualTo(UPDATED_D_OPTION);
        assertThat(testImProjects.getdFilter()).isEqualTo(UPDATED_D_FILTER);
        assertThat(testImProjects.getdId()).isEqualTo(UPDATED_D_ID);
        assertThat(testImProjects.gettType()).isEqualTo(UPDATED_T_TYPE);
        assertThat(testImProjects.gettOption()).isEqualTo(UPDATED_T_OPTION);
        assertThat(testImProjects.gettFilter()).isEqualTo(UPDATED_T_FILTER);
        assertThat(testImProjects.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testImProjects.getRisktype()).isEqualTo(UPDATED_RISKTYPE);
        assertThat(testImProjects.getRiskimpact()).isEqualTo(UPDATED_RISKIMPACT);
        assertThat(testImProjects.getRiskprobability()).isEqualTo(UPDATED_RISKPROBABILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingImProjects() throws Exception {
        int databaseSizeBeforeUpdate = imProjectsRepository.findAll().size();

        // Create the ImProjects

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImProjectsMockMvc.perform(put("/api/im-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imProjects)))
            .andExpect(status().isBadRequest());

        // Validate the ImProjects in the database
        List<ImProjects> imProjectsList = imProjectsRepository.findAll();
        assertThat(imProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImProjects() throws Exception {
        // Initialize the database
        imProjectsService.save(imProjects);

        int databaseSizeBeforeDelete = imProjectsRepository.findAll().size();

        // Delete the imProjects
        restImProjectsMockMvc.perform(delete("/api/im-projects/{id}", imProjects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ImProjects> imProjectsList = imProjectsRepository.findAll();
        assertThat(imProjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImProjects.class);
        ImProjects imProjects1 = new ImProjects();
        imProjects1.setId(1L);
        ImProjects imProjects2 = new ImProjects();
        imProjects2.setId(imProjects1.getId());
        assertThat(imProjects1).isEqualTo(imProjects2);
        imProjects2.setId(2L);
        assertThat(imProjects1).isNotEqualTo(imProjects2);
        imProjects1.setId(null);
        assertThat(imProjects1).isNotEqualTo(imProjects2);
    }
}
