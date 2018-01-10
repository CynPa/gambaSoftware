/*    1:     */ package com.asinfo.as2.entities.aerolineas;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.EntidadBase;
/*    4:     */ import java.math.BigDecimal;
/*    5:     */ import java.util.Date;
/*    6:     */ import javax.persistence.Column;
/*    7:     */ import javax.persistence.Entity;
/*    8:     */ import javax.persistence.FetchType;
/*    9:     */ import javax.persistence.GeneratedValue;
/*   10:     */ import javax.persistence.GenerationType;
/*   11:     */ import javax.persistence.Id;
/*   12:     */ import javax.persistence.JoinColumn;
/*   13:     */ import javax.persistence.ManyToOne;
/*   14:     */ import javax.persistence.Table;
/*   15:     */ import javax.persistence.TableGenerator;
/*   16:     */ import javax.persistence.Temporal;
/*   17:     */ import javax.persistence.TemporalType;
/*   18:     */ import javax.validation.constraints.Digits;
/*   19:     */ 
/*   20:     */ @Entity
/*   21:     */ @Table(name="detalle_ticket")
/*   22:     */ public class DetalleTicket
/*   23:     */   extends EntidadBase
/*   24:     */ {
/*   25:     */   private static final long serialVersionUID = 1L;
/*   26:     */   @Id
/*   27:     */   @TableGenerator(name="detalle_ticket", initialValue=0, allocationSize=50)
/*   28:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_ticket")
/*   29:     */   @Column(name="id_detalle_ticket", unique=true, nullable=false)
/*   30:     */   private int idDetalleTicket;
/*   31:     */   @Column(name="id_organizacion", nullable=false)
/*   32:     */   private int idOrganizacion;
/*   33:     */   @Column(name="id_sucursal", nullable=false)
/*   34:     */   private int idSucursal;
/*   35:     */   @Column(name="std_msg_id", nullable=true, length=1000)
/*   36:     */   private String stdMsgId;
/*   37:     */   @Column(name="seq_num", nullable=true, length=1000)
/*   38:     */   private String seqNum;
/*   39:     */   @Column(name="std_num_qual", nullable=true, length=1000)
/*   40:     */   private String stdNumQual;
/*   41:     */   @Column(name="bsp_id", nullable=true, length=1000)
/*   42:     */   private String bspId;
/*   43:     */   @Column(name="tktng_aln_cd_num", nullable=true, length=1000)
/*   44:     */   private String tktngAlnCdNum;
/*   45:     */   @Column(name="entire_rcrd", nullable=true, length=1000)
/*   46:     */   private String entireRcrd;
/*   47:     */   @Column(name="proc_date_id", nullable=true, length=1000)
/*   48:     */   private String procDateId;
/*   49:     */   @Column(name="proc_cycle_id", nullable=true, length=1000)
/*   50:     */   private String procCycleId;
/*   51:     */   @Column(name="agent_num_cd", nullable=true, length=1000)
/*   52:     */   private String agentNumCd;
/*   53:     */   @Temporal(TemporalType.DATE)
/*   54:     */   @Column(name="remt_ped_end_date", nullable=true)
/*   55:     */   private Date remtPedEndDate;
/*   56:     */   @Column(name="curr_type", nullable=true, length=1000)
/*   57:     */   private String currType;
/*   58:     */   @Column(name="trans_num", nullable=true, length=1000)
/*   59:     */   private String transNum;
/*   60:     */   @Column(name="orgnl_trans_num", nullable=true, length=1000)
/*   61:     */   private String orgnlTransNum;
/*   62:     */   @Temporal(TemporalType.DATE)
/*   63:     */   @Column(name="date_of_issue", nullable=true)
/*   64:     */   private Date dateOfIssue;
/*   65:     */   @Column(name="tkt_doc_num", nullable=true, length=1000)
/*   66:     */   private String tktDocNum;
/*   67:     */   @Column(name="tkt_doc_num_chk_dgt", nullable=true, length=1000)
/*   68:     */   private String tktDocNumChkDgt;
/*   69:     */   @Column(name="trans_cd", nullable=true, length=1000)
/*   70:     */   private String transCd;
/*   71:     */   @Column(name="fop_info", nullable=true, length=1000)
/*   72:     */   private String fopInfo;
/*   73:     */   @Digits(integer=12, fraction=6)
/*   74:     */   @Column(name="gross_value_amt")
/*   75:  98 */   private BigDecimal grossValueAmt = BigDecimal.ZERO;
/*   76:     */   @Digits(integer=12, fraction=6)
/*   77:     */   @Column(name="tot_remt_amt")
/*   78: 102 */   private BigDecimal totRemtAmt = BigDecimal.ZERO;
/*   79:     */   @Digits(integer=12, fraction=6)
/*   80:     */   @Column(name="tot_comm_value_amt")
/*   81: 106 */   private BigDecimal totCommValueAmt = BigDecimal.ZERO;
/*   82:     */   @Digits(integer=12, fraction=6)
/*   83:     */   @Column(name="tot_tax_or_misc_fee_amt")
/*   84: 110 */   private BigDecimal totTaxOrMiscFeeAmt = BigDecimal.ZERO;
/*   85:     */   @Digits(integer=12, fraction=6)
/*   86:     */   @Column(name="tot_late_rptg_pnlt")
/*   87: 114 */   private BigDecimal totLateRptgPnlt = BigDecimal.ZERO;
/*   88:     */   @Column(name="office_cnt", nullable=true, length=1000)
/*   89:     */   private String officeCnt;
/*   90:     */   @Column(name="handbook_revision_num", nullable=true, length=1000)
/*   91:     */   private String handbookRevisionNum;
/*   92:     */   @Column(name="test_prod", nullable=true, length=1000)
/*   93:     */   private String testProd;
/*   94:     */   @Temporal(TemporalType.DATE)
/*   95:     */   @Column(name="proc_date", nullable=true)
/*   96:     */   private Date procDate;
/*   97:     */   @Column(name="proc_time", nullable=true, length=1000)
/*   98:     */   private String procTime;
/*   99:     */   @Column(name="iso_cntry_cd", nullable=true, length=1000)
/*  100:     */   private String isoCntryCd;
/*  101:     */   @Column(name="file_seq_num", nullable=true, length=1000)
/*  102:     */   private String fileSeqNum;
/*  103:     */   @Temporal(TemporalType.DATE)
/*  104:     */   @Column(name="bill_anal_end_date", nullable=true)
/*  105:     */   private Date billAnalEndDate;
/*  106:     */   @Column(name="dynamic_runId", nullable=true, length=1000)
/*  107:     */   private String dynamicRunId;
/*  108:     */   @Column(name="multi_loc_identifer", nullable=true, length=1000)
/*  109:     */   private String multiLocIdentifer;
/*  110:     */   @Column(name="file_defn_id", nullable=true, length=1000)
/*  111:     */   private String fileDefnId;
/*  112:     */   @Column(name="trans_ref_num", nullable=true, length=1000)
/*  113:     */   private String transRefNum;
/*  114:     */   @Column(name="net_rptg_ind", nullable=true, length=1000)
/*  115:     */   private String netRptgInd;
/*  116:     */   @Column(name="trans_rcrd_cnt", nullable=true, length=1000)
/*  117:     */   private String transRcrdCnt;
/*  118:     */   @Column(name="format_id", nullable=true, length=1000)
/*  119:     */   private String formatId;
/*  120:     */   @Column(name="audit_stat_ind", nullable=true, length=1000)
/*  121:     */   private String auditStatInd;
/*  122:     */   @Column(name="comc_gmt_ref", nullable=true, length=1000)
/*  123:     */   private String comcAgmtRef;
/*  124:     */   @Column(name="cust_file_ref", nullable=true, length=1000)
/*  125:     */   private String custFileRef;
/*  126:     */   @Column(name="clnt_id", nullable=true, length=1000)
/*  127:     */   private String clntId;
/*  128:     */   @Column(name="rptg_sys_id", nullable=true, length=1000)
/*  129:     */   private String rptgSysId;
/*  130:     */   @Column(name="aprvd_loc_num_cd1", nullable=true, length=1000)
/*  131:     */   private String aprvdLocNumCd1;
/*  132:     */   @Column(name="aprvd_loc_type1", nullable=true, length=1000)
/*  133:     */   private String aprvdLocType1;
/*  134:     */   @Column(name="aprvd_loc_num_cd2", nullable=true, length=1000)
/*  135:     */   private String aprvdLocNumCd2;
/*  136:     */   @Column(name="aprvd_loc_type2", nullable=true, length=1000)
/*  137:     */   private String aprvdLocType2;
/*  138:     */   @Column(name="aprvd_loc_num_cd3", nullable=true, length=1000)
/*  139:     */   private String aprvdLocNumCd3;
/*  140:     */   @Column(name="aprvd_loc_type3", nullable=true, length=1000)
/*  141:     */   private String aprvdLocType3;
/*  142:     */   @Column(name="data_input_stat_ind", nullable=true, length=1000)
/*  143:     */   private String dataInputStatInd;
/*  144:     */   @Column(name="trmsn_ctrl_num", nullable=true, length=1000)
/*  145:     */   private String trmsnCtrlNum;
/*  146:     */   @Column(name="trmsn_ctrl_num_chk_dgt", nullable=true, length=1000)
/*  147:     */   private String trmsnCtrlNumChkDgt;
/*  148:     */   @Column(name="cpn_use_ind", nullable=true, length=1000)
/*  149:     */   private String cpnUseInd;
/*  150:     */   @Column(name="conj_tkt_ind", nullable=true, length=1000)
/*  151:     */   private String conjTktInd;
/*  152:     */   @Column(name="tour_cd", nullable=true, length=1000)
/*  153:     */   private String tourCd;
/*  154:     */   @Column(name="true_orig_dest_city_cd", nullable=true, length=1000)
/*  155:     */   private String trueOrigDestCityCd;
/*  156:     */   @Column(name="intl_sale_ind", nullable=true, length=1000)
/*  157:     */   private String intlSaleInd;
/*  158:     */   @Column(name="pnr_ref_and_or_aln_data", nullable=true, length=1000)
/*  159:     */   private String pnrRefAndOrAlnData;
/*  160:     */   @Digits(integer=12, fraction=6)
/*  161:     */   @Column(name="comm_amt")
/*  162: 233 */   private BigDecimal commAmt = BigDecimal.ZERO;
/*  163:     */   @Digits(integer=12, fraction=6)
/*  164:     */   @Column(name="net_fare_amt")
/*  165: 237 */   private BigDecimal netFareAmt = BigDecimal.ZERO;
/*  166:     */   @Column(name="tax_misc_fee_type1", nullable=true, length=1000)
/*  167:     */   private String taxMiscFeeType1;
/*  168:     */   @Digits(integer=12, fraction=6)
/*  169:     */   @Column(name="tax_misc_fee_amt1")
/*  170: 244 */   private BigDecimal taxMiscFeeAmt1 = BigDecimal.ZERO;
/*  171:     */   @Digits(integer=12, fraction=6)
/*  172:     */   @Column(name="ticket_doc_amt")
/*  173: 255 */   private BigDecimal ticketDocAmt = BigDecimal.ZERO;
/*  174:     */   @Digits(integer=12, fraction=6)
/*  175:     */   @Column(name="late_rptg_pnlt")
/*  176: 259 */   private BigDecimal lateRptgPnlt = BigDecimal.ZERO;
/*  177:     */   @Column(name="stscl_cd", nullable=true, length=1000)
/*  178:     */   private String stsclCd;
/*  179:     */   @Column(name="comm_type", nullable=true, length=1000)
/*  180:     */   private String commType;
/*  181:     */   @Column(name="comm_rt", nullable=true, length=1000)
/*  182:     */   private String commRt;
/*  183:     */   @Column(name="suplm_type", nullable=true, length=1000)
/*  184:     */   private String suplmType;
/*  185:     */   @Column(name="suplm_rt", nullable=true, length=1000)
/*  186:     */   private String suplmRt;
/*  187:     */   @Digits(integer=12, fraction=6)
/*  188:     */   @Column(name="suplm_amt")
/*  189: 279 */   private BigDecimal suplmAmt = BigDecimal.ZERO;
/*  190:     */   @Column(name="eff_comm_rt", nullable=true, length=1000)
/*  191:     */   private String effCommRt;
/*  192:     */   @Digits(integer=12, fraction=6)
/*  193:     */   @Column(name="eff_comm_amt")
/*  194: 286 */   private BigDecimal effCommAmt = BigDecimal.ZERO;
/*  195:     */   @Digits(integer=12, fraction=6)
/*  196:     */   @Column(name="amt_paid_by_cust")
/*  197: 290 */   private BigDecimal amtPaidByCust = BigDecimal.ZERO;
/*  198:     */   @Column(name="tax_on_comm_type1", nullable=true, length=1000)
/*  199:     */   private String taxOnCommType1;
/*  200:     */   @Digits(integer=12, fraction=6)
/*  201:     */   @Column(name="tax_on_comm_amt1")
/*  202: 298 */   private BigDecimal taxOnCommAmt1 = BigDecimal.ZERO;
/*  203:     */   @Column(name="tax_on_comm_type2", nullable=true, length=1000)
/*  204:     */   private String taxOnCommType2;
/*  205:     */   @Digits(integer=12, fraction=6)
/*  206:     */   @Column(name="tax_on_comm_amt2")
/*  207: 305 */   private BigDecimal taxOnCommAmt2 = BigDecimal.ZERO;
/*  208:     */   @Column(name="tax_on_comm_type3", nullable=true, length=1000)
/*  209:     */   private String taxOnCommType3;
/*  210:     */   @Digits(integer=12, fraction=6)
/*  211:     */   @Column(name="tax_on_comm_amt3")
/*  212: 312 */   private BigDecimal taxOnCommAmt3 = BigDecimal.ZERO;
/*  213:     */   @Column(name="tax_on_comm_type4", nullable=true, length=1000)
/*  214:     */   private String taxOnCommType4;
/*  215:     */   @Digits(integer=12, fraction=6)
/*  216:     */   @Column(name="tax_on_comm_amt4")
/*  217: 319 */   private BigDecimal taxOnCommAmt4 = BigDecimal.ZERO;
/*  218:     */   @Column(name="relt_tkt_doc_num", nullable=true, length=1000)
/*  219:     */   private String reltTktDocNum;
/*  220:     */   @Column(name="relt_tkt_doc_num_chk_dgt", nullable=true, length=1000)
/*  221:     */   private String reltTktDocNumChkDgt;
/*  222:     */   @Temporal(TemporalType.DATE)
/*  223:     */   @Column(name="date_of_issue_refund_doc", nullable=true)
/*  224:     */   private Date dateOfIssueRefundDoc;
/*  225:     */   @Column(name="relt_tkt_doc_num_id", nullable=true, length=1000)
/*  226:     */   private String reltTktDocNumId;
/*  227:     */   @Column(name="orignl_issue_info", nullable=true, length=1000)
/*  228:     */   private String orignlIssueInfo;
/*  229:     */   @Column(name="endorse_rstrs", nullable=true, length=1000)
/*  230:     */   private String endorseRstrs;
/*  231:     */   @Column(name="numero_aerolinea", nullable=true, length=1000)
/*  232:     */   private String numeroAerolinea;
/*  233:     */   @Column(name="seg_id", nullable=true, length=1000)
/*  234:     */   private String segId;
/*  235:     */   @Column(name="stpovr_cd", nullable=true, length=1000)
/*  236:     */   private String stpovrCd;
/*  237:     */   @Column(name="not_valid_before_date", nullable=true, length=1000)
/*  238:     */   private String notValidBeforeDate;
/*  239:     */   @Column(name="not_valid_after_date", nullable=true, length=1000)
/*  240:     */   private String notValidAfterDate;
/*  241:     */   @Column(name="orig_cd", nullable=true, length=1000)
/*  242:     */   private String origCd;
/*  243:     */   @Column(name="dest_cd", nullable=true, length=1000)
/*  244:     */   private String destCd;
/*  245:     */   @Column(name="carr_cd", nullable=true, length=1000)
/*  246:     */   private String carrCd;
/*  247:     */   @Column(name="flt_num", nullable=true, length=1000)
/*  248:     */   private String fltNum;
/*  249:     */   @Column(name="rsrv_bkng_dsgn", nullable=true, length=1000)
/*  250:     */   private String rsrvBkngDsgn;
/*  251:     */   @Column(name="flight_date", nullable=true, length=1000)
/*  252:     */   private String flightDate;
/*  253:     */   @Column(name="flight_dprt_time", nullable=true, length=1000)
/*  254:     */   private String flightDprtTime;
/*  255:     */   @Column(name="flight_bkng_stat", nullable=true, length=1000)
/*  256:     */   private String flightBkngStat;
/*  257:     */   @Column(name="free_bag_alwnc", nullable=true, length=1000)
/*  258:     */   private String freeBagAlwnc;
/*  259:     */   @Column(name="fb_or_tkt_dsgn", nullable=true, length=1000)
/*  260:     */   private String fbOrTktDsgn;
/*  261:     */   @Column(name="frqt_flyer_ref", nullable=true, length=1000)
/*  262:     */   private String frqtFlyerRef;
/*  263:     */   @Column(name="fare", nullable=true, length=1000)
/*  264:     */   private String fare;
/*  265:     */   @Column(name="tktng_mode_ind", nullable=true, length=1000)
/*  266:     */   private String tktngModeInd;
/*  267:     */   @Column(name="equiv_fare_paid", nullable=true, length=1000)
/*  268:     */   private String equivFarePaid;
/*  269:     */   @Column(name="tax1", nullable=true, length=1000)
/*  270:     */   private String tax1;
/*  271:     */   @Column(name="tax2", nullable=true, length=1000)
/*  272:     */   private String tax2;
/*  273:     */   @Column(name="tax3", nullable=true, length=1000)
/*  274:     */   private String tax3;
/*  275:     */   @Column(name="tot", nullable=true, length=1000)
/*  276:     */   private String tot;
/*  277:     */   @Column(name="neutral_tktng_sys_id", nullable=true, length=1000)
/*  278:     */   private String neutralTktngSysId;
/*  279:     */   @Column(name="svc_aln_or_sys_prvdr_id", nullable=true, length=1000)
/*  280:     */   private String svcAlnOrSysPrvdrId;
/*  281:     */   @Column(name="fare_calc_mode_ind", nullable=true, length=1000)
/*  282:     */   private String fareCalcModeInd;
/*  283:     */   @Column(name="bkng_agent_id", nullable=true, length=1000)
/*  284:     */   private String bkngAgentId;
/*  285:     */   @Column(name="bkng_agcy_or_loc_num", nullable=true, length=1000)
/*  286:     */   private String bkngAgcyOrLocNum;
/*  287:     */   @Column(name="bkng_ent_outl_type", nullable=true, length=1000)
/*  288:     */   private String bkngEntOutlType;
/*  289:     */   @Column(name="pax_name", nullable=true, length=1000)
/*  290:     */   private String paxName;
/*  291:     */   @Column(name="pax_specific_data", nullable=true, length=1000)
/*  292:     */   private String paxSpecificData;
/*  293:     */   @Column(name="fop_seq_num", nullable=true, length=1000)
/*  294:     */   private String fopSeqNum;
/*  295:     */   @Column(name="rsn_for_issu_desc", nullable=true, length=1000)
/*  296:     */   private String rsnForIssuDesc;
/*  297:     */   @Column(name="rsn_for_issu_cd", nullable=true, length=1000)
/*  298:     */   private String rsnForIssuCd;
/*  299:     */   @Column(name="in_conn_with_doc_num", nullable=true, length=1000)
/*  300:     */   private String inConnWithDocNum;
/*  301:     */   @Column(name="in_conn_with_doc_num_chk_dgt", nullable=true, length=1000)
/*  302:     */   private String inConnWithDocNumChkDgt;
/*  303:     */   @Column(name="bank_exch_rt", nullable=true, length=1000)
/*  304:     */   private String bankExchRt;
/*  305:     */   @Column(name="optn_agcy_or_aln_info", nullable=true, length=1000)
/*  306:     */   private String optnAgcyOrAlnInfo;
/*  307:     */   @Column(name="cpn_num", nullable=true, length=1000)
/*  308:     */   private String cpnNum;
/*  309:     */   @Column(name="srvc_prvdr_info", nullable=true, length=1000)
/*  310:     */   private String srvcPrvdrInfo;
/*  311:     */   @Column(name="print_line_id", nullable=true, length=1000)
/*  312:     */   private String printLineId;
/*  313:     */   @Column(name="print_line_text", nullable=true, length=1000)
/*  314:     */   private String printLineText;
/*  315:     */   @Column(name="fare_calc_seq_num", nullable=true, length=1000)
/*  316:     */   private String fareCalcSeqNum;
/*  317:     */   @Column(name="fare_calc_area", nullable=true, length=1000)
/*  318:     */   private String fareCalcArea;
/*  319:     */   @Column(name="sttl_auth_cd", nullable=true, length=1000)
/*  320:     */   private String sttlAuthCd;
/*  321:     */   @Column(name="fop_type", nullable=true, length=1000)
/*  322:     */   private String fopType;
/*  323:     */   @Digits(integer=12, fraction=6)
/*  324:     */   @Column(name="fop_amt")
/*  325: 500 */   private BigDecimal fopAmt = BigDecimal.ZERO;
/*  326:     */   @Column(name="fop_acct_num", nullable=true, length=1000)
/*  327:     */   private String fopAcctNum;
/*  328:     */   @Column(name="exp_date", nullable=true, length=1000)
/*  329:     */   private String expDate;
/*  330:     */   @Column(name="approval_cd", nullable=true, length=1000)
/*  331:     */   private String approvalCd;
/*  332:     */   @Column(name="inv_num", nullable=true, length=1000)
/*  333:     */   private String invNum;
/*  334:     */   @Temporal(TemporalType.DATE)
/*  335:     */   @Column(name="inv_date", nullable=true)
/*  336:     */   private Date invDate;
/*  337:     */   @Digits(integer=12, fraction=6)
/*  338:     */   @Column(name="signed_for_amt")
/*  339: 520 */   private BigDecimal signedForAmt = BigDecimal.ZERO;
/*  340:     */   @Digits(integer=12, fraction=6)
/*  341:     */   @Column(name="remt_amt")
/*  342: 524 */   private BigDecimal remtAmt = BigDecimal.ZERO;
/*  343:     */   @Column(name="fop_curr_type", nullable=true, length=1000)
/*  344:     */   private String fopCurrType;
/*  345:     */   @Digits(integer=12, fraction=6)
/*  346:     */   @Column(name="tot_tax_comm_amt")
/*  347: 533 */   private BigDecimal totTaxCommAmt = BigDecimal.ZERO;
/*  348:     */   @Digits(integer=12, fraction=6)
/*  349:     */   @Column(name="tot_misc_fee_amt")
/*  350: 539 */   private BigDecimal totMiscFeeAmt = BigDecimal.ZERO;
/*  351:     */   @Digits(integer=12, fraction=6)
/*  352:     */   @Column(name="tot_tax_on_comm_amt")
/*  353: 543 */   private BigDecimal totTaxOnCommAmt = BigDecimal.ZERO;
/*  354:     */   @Column(name="auto_reprice_ind", nullable=true, length=1000)
/*  355:     */   private String autoRepriceInd;
/*  356:     */   @Column(name="time_of_issue", nullable=true, length=1000)
/*  357:     */   private String timeOfIssue;
/*  358:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  359:     */   @JoinColumn(name="id_ticket", nullable=true)
/*  360:     */   private Ticket ticket;
/*  361:     */   @Column(name="routing_di_ind", nullable=true, length=1000)
/*  362:     */   private String routingDiInd;
/*  363:     */   @Column(name="fare_comp_priced_pax_type", nullable=true, length=1000)
/*  364:     */   private String fareCompPricedPaxType;
/*  365:     */   @Column(name="pax_type_cd", nullable=true, length=1000)
/*  366:     */   private String paxTypeCd;
/*  367:     */   @Column(name="emd_coup_num", nullable=true, length=1000)
/*  368:     */   private String emdCoupNum;
/*  369:     */   @Digits(integer=12, fraction=6)
/*  370:     */   @Column(name="emd_coup_val")
/*  371: 571 */   private BigDecimal emdCoupVal = BigDecimal.ZERO;
/*  372:     */   @Column(name="emd_relt_tkt_doc_num", nullable=true, length=1000)
/*  373:     */   private String emdReltTktDocNum;
/*  374:     */   @Column(name="emd_relt_cpn_num", nullable=true, length=1000)
/*  375:     */   private String emdReltCpnNum;
/*  376:     */   @Column(name="emd_ser_typ", nullable=true, length=1000)
/*  377:     */   private String emdSerTyp;
/*  378:     */   @Column(name="emd_rsn_isu_sub_cd", nullable=true, length=1000)
/*  379:     */   private String emdRsnIsuSubCd;
/*  380:     */   @Column(name="emd_fee_ow_air_desig", nullable=true, length=1000)
/*  381:     */   private String emdFeeOwAirDesig;
/*  382:     */   @Column(name="emd_ex_bag_ovr_allw_qual", nullable=true, length=1000)
/*  383:     */   private String emdExBagOvrAllwQual;
/*  384:     */   @Column(name="emd_ex_bag_curr_cd", nullable=true, length=1000)
/*  385:     */   private String emdExBagCurrCd;
/*  386:     */   @Column(name="emd_ex_bag_rpu", nullable=true, length=1000)
/*  387:     */   private String emdExBagRpu;
/*  388:     */   @Column(name="emd_ex_bag_tot_num_excs", nullable=true, length=1000)
/*  389:     */   private String emdExBagTotNumExcs;
/*  390:     */   @Column(name="emd_consu_iss_ind", nullable=true, length=1000)
/*  391:     */   private String emdConsuIssInd;
/*  392:     */   @Column(name="emd_no_of_ser", nullable=true, length=1000)
/*  393:     */   private String emdNoOfSer;
/*  394:     */   @Column(name="emd_opp_carr", nullable=true, length=1000)
/*  395:     */   private String emdOppCarr;
/*  396:     */   @Column(name="emd_att_grp", nullable=true, length=1000)
/*  397:     */   private String emdAttGrp;
/*  398:     */   @Column(name="emd_att_sub_grp", nullable=true, length=1000)
/*  399:     */   private String emdAttSubGrp;
/*  400:     */   @Column(name="emd_ind_carr_ind", nullable=true, length=1000)
/*  401:     */   private String emdIndCarrInd;
/*  402:     */   @Column(name="extd_pymt_cd", nullable=true, length=1000)
/*  403:     */   private String ExtdPymtCd;
/*  404:     */   @Column(name="fop_trans_identifier", nullable=true, length=1000)
/*  405:     */   private String fopTransIdentifier;
/*  406:     */   @Column(name="rec_loc", nullable=true, length=1000)
/*  407:     */   private String recLoc;
/*  408:     */   @Column(name="indicador_nacional", nullable=true)
/*  409:     */   private Boolean indicadorNacional;
/*  410:     */   
/*  411:     */   public String getRecLoc()
/*  412:     */   {
/*  413: 641 */     return this.recLoc;
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void setRecLoc(String recLoc)
/*  417:     */   {
/*  418: 645 */     this.recLoc = recLoc;
/*  419:     */   }
/*  420:     */   
/*  421:     */   public String getStdMsgId()
/*  422:     */   {
/*  423: 649 */     return this.stdMsgId;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void setStdMsgId(String stdMsgId)
/*  427:     */   {
/*  428: 653 */     this.stdMsgId = stdMsgId;
/*  429:     */   }
/*  430:     */   
/*  431:     */   public String getSeqNum()
/*  432:     */   {
/*  433: 657 */     return this.seqNum;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void setSeqNum(String seqNum)
/*  437:     */   {
/*  438: 661 */     this.seqNum = seqNum;
/*  439:     */   }
/*  440:     */   
/*  441:     */   public String getStdNumQual()
/*  442:     */   {
/*  443: 665 */     return this.stdNumQual;
/*  444:     */   }
/*  445:     */   
/*  446:     */   public void setStdNumQual(String stdNumQual)
/*  447:     */   {
/*  448: 669 */     this.stdNumQual = stdNumQual;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public String getBspId()
/*  452:     */   {
/*  453: 673 */     return this.bspId;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void setBspId(String bspId)
/*  457:     */   {
/*  458: 677 */     this.bspId = bspId;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public String getTktngAlnCdNum()
/*  462:     */   {
/*  463: 681 */     return this.tktngAlnCdNum;
/*  464:     */   }
/*  465:     */   
/*  466:     */   public void setTktngAlnCdNum(String tktngAlnCdNum)
/*  467:     */   {
/*  468: 685 */     this.tktngAlnCdNum = tktngAlnCdNum;
/*  469:     */   }
/*  470:     */   
/*  471:     */   public String getEntireRcrd()
/*  472:     */   {
/*  473: 689 */     return this.entireRcrd;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public void setEntireRcrd(String entireRcrd)
/*  477:     */   {
/*  478: 693 */     this.entireRcrd = entireRcrd;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public String getProcDateId()
/*  482:     */   {
/*  483: 697 */     return this.procDateId;
/*  484:     */   }
/*  485:     */   
/*  486:     */   public void setProcDateId(String procDateId)
/*  487:     */   {
/*  488: 701 */     this.procDateId = procDateId;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public String getProcCycleId()
/*  492:     */   {
/*  493: 705 */     return this.procCycleId;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public void setProcCycleId(String procCycleId)
/*  497:     */   {
/*  498: 709 */     this.procCycleId = procCycleId;
/*  499:     */   }
/*  500:     */   
/*  501:     */   public String getAgentNumCd()
/*  502:     */   {
/*  503: 713 */     return this.agentNumCd;
/*  504:     */   }
/*  505:     */   
/*  506:     */   public void setAgentNumCd(String agentNumCd)
/*  507:     */   {
/*  508: 717 */     this.agentNumCd = agentNumCd;
/*  509:     */   }
/*  510:     */   
/*  511:     */   public Date getRemtPedEndDate()
/*  512:     */   {
/*  513: 721 */     return this.remtPedEndDate;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public void setRemtPedEndDate(Date remtPedEndDate)
/*  517:     */   {
/*  518: 725 */     this.remtPedEndDate = remtPedEndDate;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public String getCurrType()
/*  522:     */   {
/*  523: 729 */     return this.currType;
/*  524:     */   }
/*  525:     */   
/*  526:     */   public void setCurrType(String currType)
/*  527:     */   {
/*  528: 733 */     this.currType = currType;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public String getTransNum()
/*  532:     */   {
/*  533: 737 */     return this.transNum;
/*  534:     */   }
/*  535:     */   
/*  536:     */   public void setTransNum(String transNum)
/*  537:     */   {
/*  538: 741 */     this.transNum = transNum;
/*  539:     */   }
/*  540:     */   
/*  541:     */   public String getOrgnlTransNum()
/*  542:     */   {
/*  543: 745 */     return this.orgnlTransNum;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void setOrgnlTransNum(String orgnlTransNum)
/*  547:     */   {
/*  548: 749 */     this.orgnlTransNum = orgnlTransNum;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public Date getDateOfIssue()
/*  552:     */   {
/*  553: 753 */     return this.dateOfIssue;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public void setDateOfIssue(Date dateOfIssue)
/*  557:     */   {
/*  558: 757 */     this.dateOfIssue = dateOfIssue;
/*  559:     */   }
/*  560:     */   
/*  561:     */   public String getTktDocNum()
/*  562:     */   {
/*  563: 761 */     return this.tktDocNum;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void setTktDocNum(String tktDocNum)
/*  567:     */   {
/*  568: 765 */     this.tktDocNum = tktDocNum;
/*  569:     */   }
/*  570:     */   
/*  571:     */   public String getTktDocNumChkDgt()
/*  572:     */   {
/*  573: 769 */     return this.tktDocNumChkDgt;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void setTktDocNumChkDgt(String tktDocNumChkDgt)
/*  577:     */   {
/*  578: 773 */     this.tktDocNumChkDgt = tktDocNumChkDgt;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public String getTransCd()
/*  582:     */   {
/*  583: 777 */     return this.transCd;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void setTransCd(String transCd)
/*  587:     */   {
/*  588: 781 */     this.transCd = transCd;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public String getFopInfo()
/*  592:     */   {
/*  593: 785 */     return this.fopInfo;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public void setFopInfo(String fopInfo)
/*  597:     */   {
/*  598: 789 */     this.fopInfo = fopInfo;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public BigDecimal getGrossValueAmt()
/*  602:     */   {
/*  603: 793 */     return this.grossValueAmt;
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void setGrossValueAmt(BigDecimal grossValueAmt)
/*  607:     */   {
/*  608: 797 */     this.grossValueAmt = grossValueAmt;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public BigDecimal getTotRemtAmt()
/*  612:     */   {
/*  613: 801 */     return this.totRemtAmt;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void setTotRemtAmt(BigDecimal totRemtAmt)
/*  617:     */   {
/*  618: 805 */     this.totRemtAmt = totRemtAmt;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public BigDecimal getTotCommValueAmt()
/*  622:     */   {
/*  623: 809 */     return this.totCommValueAmt;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public void setTotCommValueAmt(BigDecimal totCommValueAmt)
/*  627:     */   {
/*  628: 813 */     this.totCommValueAmt = totCommValueAmt;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public BigDecimal getTotTaxOrMiscFeeAmt()
/*  632:     */   {
/*  633: 817 */     return this.totTaxOrMiscFeeAmt;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public void setTotTaxOrMiscFeeAmt(BigDecimal totTaxOrMiscFeeAmt)
/*  637:     */   {
/*  638: 821 */     this.totTaxOrMiscFeeAmt = totTaxOrMiscFeeAmt;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public BigDecimal getTotLateRptgPnlt()
/*  642:     */   {
/*  643: 825 */     return this.totLateRptgPnlt;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setTotLateRptgPnlt(BigDecimal totLateRptgPnlt)
/*  647:     */   {
/*  648: 829 */     this.totLateRptgPnlt = totLateRptgPnlt;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public String getOfficeCnt()
/*  652:     */   {
/*  653: 833 */     return this.officeCnt;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void setOfficeCnt(String officeCnt)
/*  657:     */   {
/*  658: 837 */     this.officeCnt = officeCnt;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public String getHandbookRevisionNum()
/*  662:     */   {
/*  663: 841 */     return this.handbookRevisionNum;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void setHandbookRevisionNum(String handbookRevisionNum)
/*  667:     */   {
/*  668: 845 */     this.handbookRevisionNum = handbookRevisionNum;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public String getTestProd()
/*  672:     */   {
/*  673: 849 */     return this.testProd;
/*  674:     */   }
/*  675:     */   
/*  676:     */   public void setTestProd(String testProd)
/*  677:     */   {
/*  678: 853 */     this.testProd = testProd;
/*  679:     */   }
/*  680:     */   
/*  681:     */   public Date getProcDate()
/*  682:     */   {
/*  683: 857 */     return this.procDate;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void setProcDate(Date procDate)
/*  687:     */   {
/*  688: 861 */     this.procDate = procDate;
/*  689:     */   }
/*  690:     */   
/*  691:     */   public String getProcTime()
/*  692:     */   {
/*  693: 865 */     return this.procTime;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public void setProcTime(String procTime)
/*  697:     */   {
/*  698: 869 */     this.procTime = procTime;
/*  699:     */   }
/*  700:     */   
/*  701:     */   public String getIsoCntryCd()
/*  702:     */   {
/*  703: 873 */     return this.isoCntryCd;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public void setIsoCntryCd(String isoCntryCd)
/*  707:     */   {
/*  708: 877 */     this.isoCntryCd = isoCntryCd;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public String getFileSeqNum()
/*  712:     */   {
/*  713: 881 */     return this.fileSeqNum;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public void setFileSeqNum(String fileSeqNum)
/*  717:     */   {
/*  718: 885 */     this.fileSeqNum = fileSeqNum;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public Date getBillAnalEndDate()
/*  722:     */   {
/*  723: 889 */     return this.billAnalEndDate;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public void setBillAnalEndDate(Date billAnalEndDate)
/*  727:     */   {
/*  728: 893 */     this.billAnalEndDate = billAnalEndDate;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public String getDynamicRunId()
/*  732:     */   {
/*  733: 897 */     return this.dynamicRunId;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public void setDynamicRunId(String dynamicRunId)
/*  737:     */   {
/*  738: 901 */     this.dynamicRunId = dynamicRunId;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public String getMultiLocIdentifer()
/*  742:     */   {
/*  743: 905 */     return this.multiLocIdentifer;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public void setMultiLocIdentifer(String multiLocIdentifer)
/*  747:     */   {
/*  748: 909 */     this.multiLocIdentifer = multiLocIdentifer;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public String getFileDefnId()
/*  752:     */   {
/*  753: 913 */     return this.fileDefnId;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void setFileDefnId(String fileDefnId)
/*  757:     */   {
/*  758: 917 */     this.fileDefnId = fileDefnId;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public String getTransRefNum()
/*  762:     */   {
/*  763: 921 */     return this.transRefNum;
/*  764:     */   }
/*  765:     */   
/*  766:     */   public void setTransRefNum(String transRefNum)
/*  767:     */   {
/*  768: 925 */     this.transRefNum = transRefNum;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public String getNetRptgInd()
/*  772:     */   {
/*  773: 929 */     return this.netRptgInd;
/*  774:     */   }
/*  775:     */   
/*  776:     */   public void setNetRptgInd(String netRptgInd)
/*  777:     */   {
/*  778: 933 */     this.netRptgInd = netRptgInd;
/*  779:     */   }
/*  780:     */   
/*  781:     */   public String getTransRcrdCnt()
/*  782:     */   {
/*  783: 937 */     return this.transRcrdCnt;
/*  784:     */   }
/*  785:     */   
/*  786:     */   public void setTransRcrdCnt(String transRcrdCnt)
/*  787:     */   {
/*  788: 941 */     this.transRcrdCnt = transRcrdCnt;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public String getFormatId()
/*  792:     */   {
/*  793: 945 */     return this.formatId;
/*  794:     */   }
/*  795:     */   
/*  796:     */   public void setFormatId(String formatId)
/*  797:     */   {
/*  798: 949 */     this.formatId = formatId;
/*  799:     */   }
/*  800:     */   
/*  801:     */   public String getAuditStatInd()
/*  802:     */   {
/*  803: 953 */     return this.auditStatInd;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public void setAuditStatInd(String auditStatInd)
/*  807:     */   {
/*  808: 957 */     this.auditStatInd = auditStatInd;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public String getComcAgmtRef()
/*  812:     */   {
/*  813: 961 */     return this.comcAgmtRef;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public void setComcAgmtRef(String comcAgmtRef)
/*  817:     */   {
/*  818: 965 */     this.comcAgmtRef = comcAgmtRef;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public String getCustFileRef()
/*  822:     */   {
/*  823: 969 */     return this.custFileRef;
/*  824:     */   }
/*  825:     */   
/*  826:     */   public void setCustFileRef(String custFileRef)
/*  827:     */   {
/*  828: 973 */     this.custFileRef = custFileRef;
/*  829:     */   }
/*  830:     */   
/*  831:     */   public String getClntId()
/*  832:     */   {
/*  833: 977 */     return this.clntId;
/*  834:     */   }
/*  835:     */   
/*  836:     */   public void setClntId(String clntId)
/*  837:     */   {
/*  838: 981 */     this.clntId = clntId;
/*  839:     */   }
/*  840:     */   
/*  841:     */   public String getRptgSysId()
/*  842:     */   {
/*  843: 985 */     return this.rptgSysId;
/*  844:     */   }
/*  845:     */   
/*  846:     */   public void setRptgSysId(String rptgSysId)
/*  847:     */   {
/*  848: 989 */     this.rptgSysId = rptgSysId;
/*  849:     */   }
/*  850:     */   
/*  851:     */   public String getAprvdLocNumCd1()
/*  852:     */   {
/*  853: 993 */     return this.aprvdLocNumCd1;
/*  854:     */   }
/*  855:     */   
/*  856:     */   public void setAprvdLocNumCd1(String aprvdLocNumCd1)
/*  857:     */   {
/*  858: 997 */     this.aprvdLocNumCd1 = aprvdLocNumCd1;
/*  859:     */   }
/*  860:     */   
/*  861:     */   public String getAprvdLocType1()
/*  862:     */   {
/*  863:1001 */     return this.aprvdLocType1;
/*  864:     */   }
/*  865:     */   
/*  866:     */   public void setAprvdLocType1(String aprvdLocType1)
/*  867:     */   {
/*  868:1005 */     this.aprvdLocType1 = aprvdLocType1;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public String getAprvdLocNumCd2()
/*  872:     */   {
/*  873:1009 */     return this.aprvdLocNumCd2;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public void setAprvdLocNumCd2(String aprvdLocNumCd2)
/*  877:     */   {
/*  878:1013 */     this.aprvdLocNumCd2 = aprvdLocNumCd2;
/*  879:     */   }
/*  880:     */   
/*  881:     */   public String getAprvdLocType2()
/*  882:     */   {
/*  883:1017 */     return this.aprvdLocType2;
/*  884:     */   }
/*  885:     */   
/*  886:     */   public void setAprvdLocType2(String aprvdLocType2)
/*  887:     */   {
/*  888:1021 */     this.aprvdLocType2 = aprvdLocType2;
/*  889:     */   }
/*  890:     */   
/*  891:     */   public String getAprvdLocNumCd3()
/*  892:     */   {
/*  893:1025 */     return this.aprvdLocNumCd3;
/*  894:     */   }
/*  895:     */   
/*  896:     */   public void setAprvdLocNumCd3(String aprvdLocNumCd3)
/*  897:     */   {
/*  898:1029 */     this.aprvdLocNumCd3 = aprvdLocNumCd3;
/*  899:     */   }
/*  900:     */   
/*  901:     */   public String getAprvdLocType3()
/*  902:     */   {
/*  903:1033 */     return this.aprvdLocType3;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void setAprvdLocType3(String aprvdLocType3)
/*  907:     */   {
/*  908:1037 */     this.aprvdLocType3 = aprvdLocType3;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public String getDataInputStatInd()
/*  912:     */   {
/*  913:1041 */     return this.dataInputStatInd;
/*  914:     */   }
/*  915:     */   
/*  916:     */   public void setDataInputStatInd(String dataInputStatInd)
/*  917:     */   {
/*  918:1045 */     this.dataInputStatInd = dataInputStatInd;
/*  919:     */   }
/*  920:     */   
/*  921:     */   public String getTrmsnCtrlNum()
/*  922:     */   {
/*  923:1049 */     return this.trmsnCtrlNum;
/*  924:     */   }
/*  925:     */   
/*  926:     */   public void setTrmsnCtrlNum(String trmsnCtrlNum)
/*  927:     */   {
/*  928:1053 */     this.trmsnCtrlNum = trmsnCtrlNum;
/*  929:     */   }
/*  930:     */   
/*  931:     */   public String getTrmsnCtrlNumChkDgt()
/*  932:     */   {
/*  933:1057 */     return this.trmsnCtrlNumChkDgt;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void setTrmsnCtrlNumChkDgt(String trmsnCtrlNumChkDgt)
/*  937:     */   {
/*  938:1061 */     this.trmsnCtrlNumChkDgt = trmsnCtrlNumChkDgt;
/*  939:     */   }
/*  940:     */   
/*  941:     */   public String getCpnUseInd()
/*  942:     */   {
/*  943:1065 */     return this.cpnUseInd;
/*  944:     */   }
/*  945:     */   
/*  946:     */   public void setCpnUseInd(String cpnUseInd)
/*  947:     */   {
/*  948:1069 */     this.cpnUseInd = cpnUseInd;
/*  949:     */   }
/*  950:     */   
/*  951:     */   public String getConjTktInd()
/*  952:     */   {
/*  953:1073 */     return this.conjTktInd;
/*  954:     */   }
/*  955:     */   
/*  956:     */   public void setConjTktInd(String conjTktInd)
/*  957:     */   {
/*  958:1077 */     this.conjTktInd = conjTktInd;
/*  959:     */   }
/*  960:     */   
/*  961:     */   public String getTourCd()
/*  962:     */   {
/*  963:1081 */     return this.tourCd;
/*  964:     */   }
/*  965:     */   
/*  966:     */   public void setTourCd(String tourCd)
/*  967:     */   {
/*  968:1085 */     this.tourCd = tourCd;
/*  969:     */   }
/*  970:     */   
/*  971:     */   public String getTrueOrigDestCityCd()
/*  972:     */   {
/*  973:1089 */     return this.trueOrigDestCityCd;
/*  974:     */   }
/*  975:     */   
/*  976:     */   public void setTrueOrigDestCityCd(String trueOrigDestCityCd)
/*  977:     */   {
/*  978:1093 */     this.trueOrigDestCityCd = trueOrigDestCityCd;
/*  979:     */   }
/*  980:     */   
/*  981:     */   public String getIntlSaleInd()
/*  982:     */   {
/*  983:1097 */     return this.intlSaleInd;
/*  984:     */   }
/*  985:     */   
/*  986:     */   public void setIntlSaleInd(String intlSaleInd)
/*  987:     */   {
/*  988:1101 */     this.intlSaleInd = intlSaleInd;
/*  989:     */   }
/*  990:     */   
/*  991:     */   public String getPnrRefAndOrAlnData()
/*  992:     */   {
/*  993:1105 */     return this.pnrRefAndOrAlnData;
/*  994:     */   }
/*  995:     */   
/*  996:     */   public void setPnrRefAndOrAlnData(String pnrRefAndOrAlnData)
/*  997:     */   {
/*  998:1109 */     this.pnrRefAndOrAlnData = pnrRefAndOrAlnData;
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public BigDecimal getCommAmt()
/* 1002:     */   {
/* 1003:1113 */     return this.commAmt;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public void setCommAmt(BigDecimal commAmt)
/* 1007:     */   {
/* 1008:1117 */     this.commAmt = commAmt;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public BigDecimal getNetFareAmt()
/* 1012:     */   {
/* 1013:1121 */     return this.netFareAmt;
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public void setNetFareAmt(BigDecimal netFareAmt)
/* 1017:     */   {
/* 1018:1125 */     this.netFareAmt = netFareAmt;
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public String getTaxMiscFeeType1()
/* 1022:     */   {
/* 1023:1129 */     return this.taxMiscFeeType1;
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public void setTaxMiscFeeType1(String taxMiscFeeType1)
/* 1027:     */   {
/* 1028:1133 */     this.taxMiscFeeType1 = taxMiscFeeType1;
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public BigDecimal getTaxMiscFeeAmt1()
/* 1032:     */   {
/* 1033:1137 */     return this.taxMiscFeeAmt1;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void setTaxMiscFeeAmt1(BigDecimal taxMiscFeeAmt1)
/* 1037:     */   {
/* 1038:1141 */     this.taxMiscFeeAmt1 = taxMiscFeeAmt1;
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public BigDecimal getTicketDocAmt()
/* 1042:     */   {
/* 1043:1161 */     return this.ticketDocAmt;
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public void setTicketDocAmt(BigDecimal ticketDocAmt)
/* 1047:     */   {
/* 1048:1165 */     this.ticketDocAmt = ticketDocAmt;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public BigDecimal getLateRptgPnlt()
/* 1052:     */   {
/* 1053:1169 */     return this.lateRptgPnlt;
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public void setLateRptgPnlt(BigDecimal lateRptgPnlt)
/* 1057:     */   {
/* 1058:1173 */     this.lateRptgPnlt = lateRptgPnlt;
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public String getStsclCd()
/* 1062:     */   {
/* 1063:1177 */     return this.stsclCd;
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public void setStsclCd(String stsclCd)
/* 1067:     */   {
/* 1068:1181 */     this.stsclCd = stsclCd;
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public String getCommType()
/* 1072:     */   {
/* 1073:1185 */     return this.commType;
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public void setCommType(String commType)
/* 1077:     */   {
/* 1078:1189 */     this.commType = commType;
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public String getCommRt()
/* 1082:     */   {
/* 1083:1193 */     return this.commRt;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public void setCommRt(String commRt)
/* 1087:     */   {
/* 1088:1197 */     this.commRt = commRt;
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public String getSuplmType()
/* 1092:     */   {
/* 1093:1201 */     return this.suplmType;
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   public void setSuplmType(String suplmType)
/* 1097:     */   {
/* 1098:1205 */     this.suplmType = suplmType;
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   public String getSuplmRt()
/* 1102:     */   {
/* 1103:1209 */     return this.suplmRt;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public void setSuplmRt(String suplmRt)
/* 1107:     */   {
/* 1108:1213 */     this.suplmRt = suplmRt;
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public BigDecimal getSuplmAmt()
/* 1112:     */   {
/* 1113:1217 */     return this.suplmAmt;
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public void setSuplmAmt(BigDecimal suplmAmt)
/* 1117:     */   {
/* 1118:1221 */     this.suplmAmt = suplmAmt;
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public String getEffCommRt()
/* 1122:     */   {
/* 1123:1225 */     return this.effCommRt;
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public void setEffCommRt(String effCommRt)
/* 1127:     */   {
/* 1128:1229 */     this.effCommRt = effCommRt;
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public BigDecimal getEffCommAmt()
/* 1132:     */   {
/* 1133:1233 */     return this.effCommAmt;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public void setEffCommAmt(BigDecimal effCommAmt)
/* 1137:     */   {
/* 1138:1237 */     this.effCommAmt = effCommAmt;
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public BigDecimal getAmtPaidByCust()
/* 1142:     */   {
/* 1143:1241 */     return this.amtPaidByCust;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public void setAmtPaidByCust(BigDecimal amtPaidByCust)
/* 1147:     */   {
/* 1148:1245 */     this.amtPaidByCust = amtPaidByCust;
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public String getTaxOnCommType1()
/* 1152:     */   {
/* 1153:1249 */     return this.taxOnCommType1;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public void setTaxOnCommType1(String taxOnCommType1)
/* 1157:     */   {
/* 1158:1253 */     this.taxOnCommType1 = taxOnCommType1;
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   public BigDecimal getTaxOnCommAmt1()
/* 1162:     */   {
/* 1163:1257 */     return this.taxOnCommAmt1;
/* 1164:     */   }
/* 1165:     */   
/* 1166:     */   public void setTaxOnCommAmt1(BigDecimal taxOnCommAmt1)
/* 1167:     */   {
/* 1168:1261 */     this.taxOnCommAmt1 = taxOnCommAmt1;
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   public String getTaxOnCommType2()
/* 1172:     */   {
/* 1173:1265 */     return this.taxOnCommType2;
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   public void setTaxOnCommType2(String taxOnCommType2)
/* 1177:     */   {
/* 1178:1269 */     this.taxOnCommType2 = taxOnCommType2;
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   public BigDecimal getTaxOnCommAmt2()
/* 1182:     */   {
/* 1183:1273 */     return this.taxOnCommAmt2;
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   public void setTaxOnCommAmt2(BigDecimal taxOnCommAmt2)
/* 1187:     */   {
/* 1188:1277 */     this.taxOnCommAmt2 = taxOnCommAmt2;
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public String getTaxOnCommType3()
/* 1192:     */   {
/* 1193:1281 */     return this.taxOnCommType3;
/* 1194:     */   }
/* 1195:     */   
/* 1196:     */   public void setTaxOnCommType3(String taxOnCommType3)
/* 1197:     */   {
/* 1198:1285 */     this.taxOnCommType3 = taxOnCommType3;
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   public BigDecimal getTaxOnCommAmt3()
/* 1202:     */   {
/* 1203:1289 */     return this.taxOnCommAmt3;
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   public void setTaxOnCommAmt3(BigDecimal taxOnCommAmt3)
/* 1207:     */   {
/* 1208:1293 */     this.taxOnCommAmt3 = taxOnCommAmt3;
/* 1209:     */   }
/* 1210:     */   
/* 1211:     */   public String getTaxOnCommType4()
/* 1212:     */   {
/* 1213:1297 */     return this.taxOnCommType4;
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   public void setTaxOnCommType4(String taxOnCommType4)
/* 1217:     */   {
/* 1218:1301 */     this.taxOnCommType4 = taxOnCommType4;
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   public BigDecimal getTaxOnCommAmt4()
/* 1222:     */   {
/* 1223:1305 */     return this.taxOnCommAmt4;
/* 1224:     */   }
/* 1225:     */   
/* 1226:     */   public void setTaxOnCommAmt4(BigDecimal taxOnCommAmt4)
/* 1227:     */   {
/* 1228:1309 */     this.taxOnCommAmt4 = taxOnCommAmt4;
/* 1229:     */   }
/* 1230:     */   
/* 1231:     */   public String getReltTktDocNum()
/* 1232:     */   {
/* 1233:1313 */     return this.reltTktDocNum;
/* 1234:     */   }
/* 1235:     */   
/* 1236:     */   public void setReltTktDocNum(String reltTktDocNum)
/* 1237:     */   {
/* 1238:1317 */     this.reltTktDocNum = reltTktDocNum;
/* 1239:     */   }
/* 1240:     */   
/* 1241:     */   public String getReltTktDocNumChkDgt()
/* 1242:     */   {
/* 1243:1321 */     return this.reltTktDocNumChkDgt;
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   public void setReltTktDocNumChkDgt(String reltTktDocNumChkDgt)
/* 1247:     */   {
/* 1248:1325 */     this.reltTktDocNumChkDgt = reltTktDocNumChkDgt;
/* 1249:     */   }
/* 1250:     */   
/* 1251:     */   public String getReltTktDocNumId()
/* 1252:     */   {
/* 1253:1329 */     return this.reltTktDocNumId;
/* 1254:     */   }
/* 1255:     */   
/* 1256:     */   public void setReltTktDocNumId(String reltTktDocNumId)
/* 1257:     */   {
/* 1258:1333 */     this.reltTktDocNumId = reltTktDocNumId;
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   public String getOrignlIssueInfo()
/* 1262:     */   {
/* 1263:1337 */     return this.orignlIssueInfo;
/* 1264:     */   }
/* 1265:     */   
/* 1266:     */   public void setOrignlIssueInfo(String orignlIssueInfo)
/* 1267:     */   {
/* 1268:1341 */     this.orignlIssueInfo = orignlIssueInfo;
/* 1269:     */   }
/* 1270:     */   
/* 1271:     */   public String getEndorseRstrs()
/* 1272:     */   {
/* 1273:1345 */     return this.endorseRstrs;
/* 1274:     */   }
/* 1275:     */   
/* 1276:     */   public void setEndorseRstrs(String endorseRstrs)
/* 1277:     */   {
/* 1278:1349 */     this.endorseRstrs = endorseRstrs;
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   public String getNumeroAerolinea()
/* 1282:     */   {
/* 1283:1353 */     return this.numeroAerolinea;
/* 1284:     */   }
/* 1285:     */   
/* 1286:     */   public void setNumeroAerolinea(String numeroAerolinea)
/* 1287:     */   {
/* 1288:1357 */     this.numeroAerolinea = numeroAerolinea;
/* 1289:     */   }
/* 1290:     */   
/* 1291:     */   public String getSegId()
/* 1292:     */   {
/* 1293:1361 */     return this.segId;
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   public void setSegId(String segId)
/* 1297:     */   {
/* 1298:1365 */     this.segId = segId;
/* 1299:     */   }
/* 1300:     */   
/* 1301:     */   public String getStpovrCd()
/* 1302:     */   {
/* 1303:1369 */     return this.stpovrCd;
/* 1304:     */   }
/* 1305:     */   
/* 1306:     */   public void setStpovrCd(String stpovrCd)
/* 1307:     */   {
/* 1308:1373 */     this.stpovrCd = stpovrCd;
/* 1309:     */   }
/* 1310:     */   
/* 1311:     */   public String getNotValidBeforeDate()
/* 1312:     */   {
/* 1313:1377 */     return this.notValidBeforeDate;
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   public void setNotValidBeforeDate(String notValidBeforeDate)
/* 1317:     */   {
/* 1318:1381 */     this.notValidBeforeDate = notValidBeforeDate;
/* 1319:     */   }
/* 1320:     */   
/* 1321:     */   public String getNotValidAfterDate()
/* 1322:     */   {
/* 1323:1385 */     return this.notValidAfterDate;
/* 1324:     */   }
/* 1325:     */   
/* 1326:     */   public void setNotValidAfterDate(String notValidAfterDate)
/* 1327:     */   {
/* 1328:1389 */     this.notValidAfterDate = notValidAfterDate;
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   public String getOrigCd()
/* 1332:     */   {
/* 1333:1393 */     return this.origCd;
/* 1334:     */   }
/* 1335:     */   
/* 1336:     */   public void setOrigCd(String origCd)
/* 1337:     */   {
/* 1338:1397 */     this.origCd = origCd;
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   public String getDestCd()
/* 1342:     */   {
/* 1343:1401 */     return this.destCd;
/* 1344:     */   }
/* 1345:     */   
/* 1346:     */   public void setDestCd(String destCd)
/* 1347:     */   {
/* 1348:1405 */     this.destCd = destCd;
/* 1349:     */   }
/* 1350:     */   
/* 1351:     */   public String getCarrCd()
/* 1352:     */   {
/* 1353:1409 */     return this.carrCd;
/* 1354:     */   }
/* 1355:     */   
/* 1356:     */   public void setCarrCd(String carrCd)
/* 1357:     */   {
/* 1358:1413 */     this.carrCd = carrCd;
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   public String getFltNum()
/* 1362:     */   {
/* 1363:1417 */     return this.fltNum;
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   public void setFltNum(String fltNum)
/* 1367:     */   {
/* 1368:1421 */     this.fltNum = fltNum;
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   public String getRsrvBkngDsgn()
/* 1372:     */   {
/* 1373:1425 */     return this.rsrvBkngDsgn;
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   public void setRsrvBkngDsgn(String rsrvBkngDsgn)
/* 1377:     */   {
/* 1378:1429 */     this.rsrvBkngDsgn = rsrvBkngDsgn;
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   public String getFlightDate()
/* 1382:     */   {
/* 1383:1433 */     return this.flightDate;
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   public void setFlightDate(String flightDate)
/* 1387:     */   {
/* 1388:1437 */     this.flightDate = flightDate;
/* 1389:     */   }
/* 1390:     */   
/* 1391:     */   public String getFlightDprtTime()
/* 1392:     */   {
/* 1393:1441 */     return this.flightDprtTime;
/* 1394:     */   }
/* 1395:     */   
/* 1396:     */   public void setFlightDprtTime(String flightDprtTime)
/* 1397:     */   {
/* 1398:1445 */     this.flightDprtTime = flightDprtTime;
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   public String getFlightBkngStat()
/* 1402:     */   {
/* 1403:1449 */     return this.flightBkngStat;
/* 1404:     */   }
/* 1405:     */   
/* 1406:     */   public void setFlightBkngStat(String flightBkngStat)
/* 1407:     */   {
/* 1408:1453 */     this.flightBkngStat = flightBkngStat;
/* 1409:     */   }
/* 1410:     */   
/* 1411:     */   public String getFreeBagAlwnc()
/* 1412:     */   {
/* 1413:1457 */     return this.freeBagAlwnc;
/* 1414:     */   }
/* 1415:     */   
/* 1416:     */   public void setFreeBagAlwnc(String freeBagAlwnc)
/* 1417:     */   {
/* 1418:1461 */     this.freeBagAlwnc = freeBagAlwnc;
/* 1419:     */   }
/* 1420:     */   
/* 1421:     */   public String getFbOrTktDsgn()
/* 1422:     */   {
/* 1423:1465 */     return this.fbOrTktDsgn;
/* 1424:     */   }
/* 1425:     */   
/* 1426:     */   public void setFbOrTktDsgn(String fbOrTktDsgn)
/* 1427:     */   {
/* 1428:1469 */     this.fbOrTktDsgn = fbOrTktDsgn;
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public String getFrqtFlyerRef()
/* 1432:     */   {
/* 1433:1473 */     return this.frqtFlyerRef;
/* 1434:     */   }
/* 1435:     */   
/* 1436:     */   public void setFrqtFlyerRef(String frqtFlyerRef)
/* 1437:     */   {
/* 1438:1477 */     this.frqtFlyerRef = frqtFlyerRef;
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public String getFare()
/* 1442:     */   {
/* 1443:1481 */     return this.fare;
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   public void setFare(String fare)
/* 1447:     */   {
/* 1448:1485 */     this.fare = fare;
/* 1449:     */   }
/* 1450:     */   
/* 1451:     */   public String getTktngModeInd()
/* 1452:     */   {
/* 1453:1489 */     return this.tktngModeInd;
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public void setTktngModeInd(String tktngModeInd)
/* 1457:     */   {
/* 1458:1493 */     this.tktngModeInd = tktngModeInd;
/* 1459:     */   }
/* 1460:     */   
/* 1461:     */   public String getEquivFarePaid()
/* 1462:     */   {
/* 1463:1497 */     return this.equivFarePaid;
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public void setEquivFarePaid(String equivFarePaid)
/* 1467:     */   {
/* 1468:1501 */     this.equivFarePaid = equivFarePaid;
/* 1469:     */   }
/* 1470:     */   
/* 1471:     */   public String getTax1()
/* 1472:     */   {
/* 1473:1505 */     return this.tax1;
/* 1474:     */   }
/* 1475:     */   
/* 1476:     */   public void setTax1(String tax1)
/* 1477:     */   {
/* 1478:1509 */     this.tax1 = tax1;
/* 1479:     */   }
/* 1480:     */   
/* 1481:     */   public String getTax2()
/* 1482:     */   {
/* 1483:1513 */     return this.tax2;
/* 1484:     */   }
/* 1485:     */   
/* 1486:     */   public void setTax2(String tax2)
/* 1487:     */   {
/* 1488:1517 */     this.tax2 = tax2;
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public String getTax3()
/* 1492:     */   {
/* 1493:1521 */     return this.tax3;
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public void setTax3(String tax3)
/* 1497:     */   {
/* 1498:1525 */     this.tax3 = tax3;
/* 1499:     */   }
/* 1500:     */   
/* 1501:     */   public String getTot()
/* 1502:     */   {
/* 1503:1529 */     return this.tot;
/* 1504:     */   }
/* 1505:     */   
/* 1506:     */   public void setTot(String tot)
/* 1507:     */   {
/* 1508:1533 */     this.tot = tot;
/* 1509:     */   }
/* 1510:     */   
/* 1511:     */   public String getNeutralTktngSysId()
/* 1512:     */   {
/* 1513:1537 */     return this.neutralTktngSysId;
/* 1514:     */   }
/* 1515:     */   
/* 1516:     */   public void setNeutralTktngSysId(String neutralTktngSysId)
/* 1517:     */   {
/* 1518:1541 */     this.neutralTktngSysId = neutralTktngSysId;
/* 1519:     */   }
/* 1520:     */   
/* 1521:     */   public String getSvcAlnOrSysPrvdrId()
/* 1522:     */   {
/* 1523:1545 */     return this.svcAlnOrSysPrvdrId;
/* 1524:     */   }
/* 1525:     */   
/* 1526:     */   public void setSvcAlnOrSysPrvdrId(String svcAlnOrSysPrvdrId)
/* 1527:     */   {
/* 1528:1549 */     this.svcAlnOrSysPrvdrId = svcAlnOrSysPrvdrId;
/* 1529:     */   }
/* 1530:     */   
/* 1531:     */   public String getFareCalcModeInd()
/* 1532:     */   {
/* 1533:1553 */     return this.fareCalcModeInd;
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public void setFareCalcModeInd(String fareCalcModeInd)
/* 1537:     */   {
/* 1538:1557 */     this.fareCalcModeInd = fareCalcModeInd;
/* 1539:     */   }
/* 1540:     */   
/* 1541:     */   public String getBkngAgentId()
/* 1542:     */   {
/* 1543:1561 */     return this.bkngAgentId;
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   public void setBkngAgentId(String bkngAgentId)
/* 1547:     */   {
/* 1548:1565 */     this.bkngAgentId = bkngAgentId;
/* 1549:     */   }
/* 1550:     */   
/* 1551:     */   public String getBkngAgcyOrLocNum()
/* 1552:     */   {
/* 1553:1569 */     return this.bkngAgcyOrLocNum;
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   public void setBkngAgcyOrLocNum(String bkngAgcyOrLocNum)
/* 1557:     */   {
/* 1558:1573 */     this.bkngAgcyOrLocNum = bkngAgcyOrLocNum;
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   public String getBkngEntOutlType()
/* 1562:     */   {
/* 1563:1577 */     return this.bkngEntOutlType;
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public void setBkngEntOutlType(String bkngEntOutlType)
/* 1567:     */   {
/* 1568:1581 */     this.bkngEntOutlType = bkngEntOutlType;
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public String getPaxName()
/* 1572:     */   {
/* 1573:1585 */     return this.paxName;
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public void setPaxName(String paxName)
/* 1577:     */   {
/* 1578:1589 */     this.paxName = paxName;
/* 1579:     */   }
/* 1580:     */   
/* 1581:     */   public String getPaxSpecificData()
/* 1582:     */   {
/* 1583:1593 */     return this.paxSpecificData;
/* 1584:     */   }
/* 1585:     */   
/* 1586:     */   public void setPaxSpecificData(String paxSpecificData)
/* 1587:     */   {
/* 1588:1597 */     this.paxSpecificData = paxSpecificData;
/* 1589:     */   }
/* 1590:     */   
/* 1591:     */   public String getFopSeqNum()
/* 1592:     */   {
/* 1593:1601 */     return this.fopSeqNum;
/* 1594:     */   }
/* 1595:     */   
/* 1596:     */   public void setFopSeqNum(String fopSeqNum)
/* 1597:     */   {
/* 1598:1605 */     this.fopSeqNum = fopSeqNum;
/* 1599:     */   }
/* 1600:     */   
/* 1601:     */   public String getRsnForIssuDesc()
/* 1602:     */   {
/* 1603:1609 */     return this.rsnForIssuDesc;
/* 1604:     */   }
/* 1605:     */   
/* 1606:     */   public void setRsnForIssuDesc(String rsnForIssuDesc)
/* 1607:     */   {
/* 1608:1613 */     this.rsnForIssuDesc = rsnForIssuDesc;
/* 1609:     */   }
/* 1610:     */   
/* 1611:     */   public String getRsnForIssuCd()
/* 1612:     */   {
/* 1613:1617 */     return this.rsnForIssuCd;
/* 1614:     */   }
/* 1615:     */   
/* 1616:     */   public void setRsnForIssuCd(String rsnForIssuCd)
/* 1617:     */   {
/* 1618:1621 */     this.rsnForIssuCd = rsnForIssuCd;
/* 1619:     */   }
/* 1620:     */   
/* 1621:     */   public String getInConnWithDocNum()
/* 1622:     */   {
/* 1623:1625 */     return this.inConnWithDocNum;
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   public void setInConnWithDocNum(String inConnWithDocNum)
/* 1627:     */   {
/* 1628:1629 */     this.inConnWithDocNum = inConnWithDocNum;
/* 1629:     */   }
/* 1630:     */   
/* 1631:     */   public String getInConnWithDocNumChkDgt()
/* 1632:     */   {
/* 1633:1633 */     return this.inConnWithDocNumChkDgt;
/* 1634:     */   }
/* 1635:     */   
/* 1636:     */   public void setInConnWithDocNumChkDgt(String inConnWithDocNumChkDgt)
/* 1637:     */   {
/* 1638:1637 */     this.inConnWithDocNumChkDgt = inConnWithDocNumChkDgt;
/* 1639:     */   }
/* 1640:     */   
/* 1641:     */   public String getBankExchRt()
/* 1642:     */   {
/* 1643:1641 */     return this.bankExchRt;
/* 1644:     */   }
/* 1645:     */   
/* 1646:     */   public void setBankExchRt(String bankExchRt)
/* 1647:     */   {
/* 1648:1645 */     this.bankExchRt = bankExchRt;
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   public String getOptnAgcyOrAlnInfo()
/* 1652:     */   {
/* 1653:1649 */     return this.optnAgcyOrAlnInfo;
/* 1654:     */   }
/* 1655:     */   
/* 1656:     */   public void setOptnAgcyOrAlnInfo(String optnAgcyOrAlnInfo)
/* 1657:     */   {
/* 1658:1653 */     this.optnAgcyOrAlnInfo = optnAgcyOrAlnInfo;
/* 1659:     */   }
/* 1660:     */   
/* 1661:     */   public String getCpnNum()
/* 1662:     */   {
/* 1663:1657 */     return this.cpnNum;
/* 1664:     */   }
/* 1665:     */   
/* 1666:     */   public void setCpnNum(String cpnNum)
/* 1667:     */   {
/* 1668:1661 */     this.cpnNum = cpnNum;
/* 1669:     */   }
/* 1670:     */   
/* 1671:     */   public String getSrvcPrvdrInfo()
/* 1672:     */   {
/* 1673:1665 */     return this.srvcPrvdrInfo;
/* 1674:     */   }
/* 1675:     */   
/* 1676:     */   public void setSrvcPrvdrInfo(String srvcPrvdrInfo)
/* 1677:     */   {
/* 1678:1669 */     this.srvcPrvdrInfo = srvcPrvdrInfo;
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   public String getPrintLineId()
/* 1682:     */   {
/* 1683:1673 */     return this.printLineId;
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   public void setPrintLineId(String printLineId)
/* 1687:     */   {
/* 1688:1677 */     this.printLineId = printLineId;
/* 1689:     */   }
/* 1690:     */   
/* 1691:     */   public String getPrintLineText()
/* 1692:     */   {
/* 1693:1681 */     return this.printLineText;
/* 1694:     */   }
/* 1695:     */   
/* 1696:     */   public void setPrintLineText(String printLineText)
/* 1697:     */   {
/* 1698:1685 */     this.printLineText = printLineText;
/* 1699:     */   }
/* 1700:     */   
/* 1701:     */   public String getFareCalcSeqNum()
/* 1702:     */   {
/* 1703:1689 */     return this.fareCalcSeqNum;
/* 1704:     */   }
/* 1705:     */   
/* 1706:     */   public void setFareCalcSeqNum(String fareCalcSeqNum)
/* 1707:     */   {
/* 1708:1693 */     this.fareCalcSeqNum = fareCalcSeqNum;
/* 1709:     */   }
/* 1710:     */   
/* 1711:     */   public String getFareCalcArea()
/* 1712:     */   {
/* 1713:1697 */     return this.fareCalcArea;
/* 1714:     */   }
/* 1715:     */   
/* 1716:     */   public void setFareCalcArea(String fareCalcArea)
/* 1717:     */   {
/* 1718:1701 */     this.fareCalcArea = fareCalcArea;
/* 1719:     */   }
/* 1720:     */   
/* 1721:     */   public String getSttlAuthCd()
/* 1722:     */   {
/* 1723:1705 */     return this.sttlAuthCd;
/* 1724:     */   }
/* 1725:     */   
/* 1726:     */   public void setSttlAuthCd(String sttlAuthCd)
/* 1727:     */   {
/* 1728:1709 */     this.sttlAuthCd = sttlAuthCd;
/* 1729:     */   }
/* 1730:     */   
/* 1731:     */   public String getFopType()
/* 1732:     */   {
/* 1733:1713 */     return this.fopType;
/* 1734:     */   }
/* 1735:     */   
/* 1736:     */   public void setFopType(String fopType)
/* 1737:     */   {
/* 1738:1717 */     this.fopType = fopType;
/* 1739:     */   }
/* 1740:     */   
/* 1741:     */   public BigDecimal getFopAmt()
/* 1742:     */   {
/* 1743:1721 */     return this.fopAmt;
/* 1744:     */   }
/* 1745:     */   
/* 1746:     */   public void setFopAmt(BigDecimal fopAmt)
/* 1747:     */   {
/* 1748:1725 */     this.fopAmt = fopAmt;
/* 1749:     */   }
/* 1750:     */   
/* 1751:     */   public String getFopAcctNum()
/* 1752:     */   {
/* 1753:1729 */     return this.fopAcctNum;
/* 1754:     */   }
/* 1755:     */   
/* 1756:     */   public void setFopAcctNum(String fopAcctNum)
/* 1757:     */   {
/* 1758:1733 */     this.fopAcctNum = fopAcctNum;
/* 1759:     */   }
/* 1760:     */   
/* 1761:     */   public String getExpDate()
/* 1762:     */   {
/* 1763:1737 */     return this.expDate;
/* 1764:     */   }
/* 1765:     */   
/* 1766:     */   public void setExpDate(String expDate)
/* 1767:     */   {
/* 1768:1741 */     this.expDate = expDate;
/* 1769:     */   }
/* 1770:     */   
/* 1771:     */   public String getApprovalCd()
/* 1772:     */   {
/* 1773:1745 */     return this.approvalCd;
/* 1774:     */   }
/* 1775:     */   
/* 1776:     */   public void setApprovalCd(String approvalCd)
/* 1777:     */   {
/* 1778:1749 */     this.approvalCd = approvalCd;
/* 1779:     */   }
/* 1780:     */   
/* 1781:     */   public String getInvNum()
/* 1782:     */   {
/* 1783:1753 */     return this.invNum;
/* 1784:     */   }
/* 1785:     */   
/* 1786:     */   public void setInvNum(String invNum)
/* 1787:     */   {
/* 1788:1757 */     this.invNum = invNum;
/* 1789:     */   }
/* 1790:     */   
/* 1791:     */   public Date getInvDate()
/* 1792:     */   {
/* 1793:1761 */     return this.invDate;
/* 1794:     */   }
/* 1795:     */   
/* 1796:     */   public void setInvDate(Date invDate)
/* 1797:     */   {
/* 1798:1765 */     this.invDate = invDate;
/* 1799:     */   }
/* 1800:     */   
/* 1801:     */   public BigDecimal getSignedForAmt()
/* 1802:     */   {
/* 1803:1769 */     return this.signedForAmt;
/* 1804:     */   }
/* 1805:     */   
/* 1806:     */   public void setSignedForAmt(BigDecimal signedForAmt)
/* 1807:     */   {
/* 1808:1773 */     this.signedForAmt = signedForAmt;
/* 1809:     */   }
/* 1810:     */   
/* 1811:     */   public BigDecimal getRemtAmt()
/* 1812:     */   {
/* 1813:1777 */     return this.remtAmt;
/* 1814:     */   }
/* 1815:     */   
/* 1816:     */   public void setRemtAmt(BigDecimal remtAmt)
/* 1817:     */   {
/* 1818:1781 */     this.remtAmt = remtAmt;
/* 1819:     */   }
/* 1820:     */   
/* 1821:     */   public String getFopCurrType()
/* 1822:     */   {
/* 1823:1785 */     return this.fopCurrType;
/* 1824:     */   }
/* 1825:     */   
/* 1826:     */   public void setFopCurrType(String fopCurrType)
/* 1827:     */   {
/* 1828:1789 */     this.fopCurrType = fopCurrType;
/* 1829:     */   }
/* 1830:     */   
/* 1831:     */   public BigDecimal getTotTaxCommAmt()
/* 1832:     */   {
/* 1833:1793 */     return this.totTaxCommAmt;
/* 1834:     */   }
/* 1835:     */   
/* 1836:     */   public void setTotTaxCommAmt(BigDecimal totTaxCommAmt)
/* 1837:     */   {
/* 1838:1797 */     this.totTaxCommAmt = totTaxCommAmt;
/* 1839:     */   }
/* 1840:     */   
/* 1841:     */   public BigDecimal getTotMiscFeeAmt()
/* 1842:     */   {
/* 1843:1801 */     return this.totMiscFeeAmt;
/* 1844:     */   }
/* 1845:     */   
/* 1846:     */   public void setTotMiscFeeAmt(BigDecimal totMiscFeeAmt)
/* 1847:     */   {
/* 1848:1805 */     this.totMiscFeeAmt = totMiscFeeAmt;
/* 1849:     */   }
/* 1850:     */   
/* 1851:     */   public BigDecimal getTotTaxOnCommAmt()
/* 1852:     */   {
/* 1853:1809 */     return this.totTaxOnCommAmt;
/* 1854:     */   }
/* 1855:     */   
/* 1856:     */   public void setTotTaxOnCommAmt(BigDecimal totTaxOnCommAmt)
/* 1857:     */   {
/* 1858:1813 */     this.totTaxOnCommAmt = totTaxOnCommAmt;
/* 1859:     */   }
/* 1860:     */   
/* 1861:     */   public Date getDateOfIssueRefundDoc()
/* 1862:     */   {
/* 1863:1817 */     return this.dateOfIssueRefundDoc;
/* 1864:     */   }
/* 1865:     */   
/* 1866:     */   public void setDateOfIssueRefundDoc(Date dateOfIssueRefundDoc)
/* 1867:     */   {
/* 1868:1821 */     this.dateOfIssueRefundDoc = dateOfIssueRefundDoc;
/* 1869:     */   }
/* 1870:     */   
/* 1871:     */   public int getId()
/* 1872:     */   {
/* 1873:1826 */     return this.idDetalleTicket;
/* 1874:     */   }
/* 1875:     */   
/* 1876:     */   public Ticket getTicket()
/* 1877:     */   {
/* 1878:1830 */     return this.ticket;
/* 1879:     */   }
/* 1880:     */   
/* 1881:     */   public void setTicket(Ticket ticket)
/* 1882:     */   {
/* 1883:1834 */     this.ticket = ticket;
/* 1884:     */   }
/* 1885:     */   
/* 1886:     */   public int getIdDetalleTicket()
/* 1887:     */   {
/* 1888:1838 */     return this.idDetalleTicket;
/* 1889:     */   }
/* 1890:     */   
/* 1891:     */   public void setIdDetalleTicket(int idDetalleTicket)
/* 1892:     */   {
/* 1893:1842 */     this.idDetalleTicket = idDetalleTicket;
/* 1894:     */   }
/* 1895:     */   
/* 1896:     */   public int getIdOrganizacion()
/* 1897:     */   {
/* 1898:1846 */     return this.idOrganizacion;
/* 1899:     */   }
/* 1900:     */   
/* 1901:     */   public void setIdOrganizacion(int idOrganizacion)
/* 1902:     */   {
/* 1903:1850 */     this.idOrganizacion = idOrganizacion;
/* 1904:     */   }
/* 1905:     */   
/* 1906:     */   public int getIdSucursal()
/* 1907:     */   {
/* 1908:1854 */     return this.idSucursal;
/* 1909:     */   }
/* 1910:     */   
/* 1911:     */   public void setIdSucursal(int idSucursal)
/* 1912:     */   {
/* 1913:1858 */     this.idSucursal = idSucursal;
/* 1914:     */   }
/* 1915:     */   
/* 1916:     */   public String getAutoRepriceInd()
/* 1917:     */   {
/* 1918:1862 */     return this.autoRepriceInd;
/* 1919:     */   }
/* 1920:     */   
/* 1921:     */   public void setAutoRepriceInd(String autoRepriceInd)
/* 1922:     */   {
/* 1923:1866 */     this.autoRepriceInd = autoRepriceInd;
/* 1924:     */   }
/* 1925:     */   
/* 1926:     */   public String getTimeOfIssue()
/* 1927:     */   {
/* 1928:1870 */     return this.timeOfIssue;
/* 1929:     */   }
/* 1930:     */   
/* 1931:     */   public void setTimeOfIssue(String timeOfIssue)
/* 1932:     */   {
/* 1933:1874 */     this.timeOfIssue = timeOfIssue;
/* 1934:     */   }
/* 1935:     */   
/* 1936:     */   public String getRoutingDiInd()
/* 1937:     */   {
/* 1938:1894 */     return this.routingDiInd;
/* 1939:     */   }
/* 1940:     */   
/* 1941:     */   public void setRoutingDiInd(String routingDiInd)
/* 1942:     */   {
/* 1943:1898 */     this.routingDiInd = routingDiInd;
/* 1944:     */   }
/* 1945:     */   
/* 1946:     */   public String getFareCompPricedPaxType()
/* 1947:     */   {
/* 1948:1902 */     return this.fareCompPricedPaxType;
/* 1949:     */   }
/* 1950:     */   
/* 1951:     */   public void setFareCompPricedPaxType(String fareCompPricedPaxType)
/* 1952:     */   {
/* 1953:1906 */     this.fareCompPricedPaxType = fareCompPricedPaxType;
/* 1954:     */   }
/* 1955:     */   
/* 1956:     */   public String getPaxTypeCd()
/* 1957:     */   {
/* 1958:1910 */     return this.paxTypeCd;
/* 1959:     */   }
/* 1960:     */   
/* 1961:     */   public void setPaxTypeCd(String paxTypeCd)
/* 1962:     */   {
/* 1963:1914 */     this.paxTypeCd = paxTypeCd;
/* 1964:     */   }
/* 1965:     */   
/* 1966:     */   public String getEmdCoupNum()
/* 1967:     */   {
/* 1968:1918 */     return this.emdCoupNum;
/* 1969:     */   }
/* 1970:     */   
/* 1971:     */   public void setEmdCoupNum(String emdCoupNum)
/* 1972:     */   {
/* 1973:1922 */     this.emdCoupNum = emdCoupNum;
/* 1974:     */   }
/* 1975:     */   
/* 1976:     */   public BigDecimal getEmdCoupVal()
/* 1977:     */   {
/* 1978:1926 */     return this.emdCoupVal;
/* 1979:     */   }
/* 1980:     */   
/* 1981:     */   public void setEmdCoupVal(BigDecimal emdCoupVal)
/* 1982:     */   {
/* 1983:1930 */     this.emdCoupVal = emdCoupVal;
/* 1984:     */   }
/* 1985:     */   
/* 1986:     */   public String getEmdReltTktDocNum()
/* 1987:     */   {
/* 1988:1934 */     return this.emdReltTktDocNum;
/* 1989:     */   }
/* 1990:     */   
/* 1991:     */   public void setEmdReltTktDocNum(String emdReltTktDocNum)
/* 1992:     */   {
/* 1993:1938 */     this.emdReltTktDocNum = emdReltTktDocNum;
/* 1994:     */   }
/* 1995:     */   
/* 1996:     */   public String getEmdReltCpnNum()
/* 1997:     */   {
/* 1998:1942 */     return this.emdReltCpnNum;
/* 1999:     */   }
/* 2000:     */   
/* 2001:     */   public void setEmdReltCpnNum(String emdReltCpnNum)
/* 2002:     */   {
/* 2003:1946 */     this.emdReltCpnNum = emdReltCpnNum;
/* 2004:     */   }
/* 2005:     */   
/* 2006:     */   public String getEmdSerTyp()
/* 2007:     */   {
/* 2008:1950 */     return this.emdSerTyp;
/* 2009:     */   }
/* 2010:     */   
/* 2011:     */   public void setEmdSerTyp(String emdSerTyp)
/* 2012:     */   {
/* 2013:1954 */     this.emdSerTyp = emdSerTyp;
/* 2014:     */   }
/* 2015:     */   
/* 2016:     */   public String getEmdRsnIsuSubCd()
/* 2017:     */   {
/* 2018:1958 */     return this.emdRsnIsuSubCd;
/* 2019:     */   }
/* 2020:     */   
/* 2021:     */   public void setEmdRsnIsuSubCd(String emdRsnIsuSubCd)
/* 2022:     */   {
/* 2023:1962 */     this.emdRsnIsuSubCd = emdRsnIsuSubCd;
/* 2024:     */   }
/* 2025:     */   
/* 2026:     */   public String getEmdFeeOwAirDesig()
/* 2027:     */   {
/* 2028:1966 */     return this.emdFeeOwAirDesig;
/* 2029:     */   }
/* 2030:     */   
/* 2031:     */   public void setEmdFeeOwAirDesig(String emdFeeOwAirDesig)
/* 2032:     */   {
/* 2033:1970 */     this.emdFeeOwAirDesig = emdFeeOwAirDesig;
/* 2034:     */   }
/* 2035:     */   
/* 2036:     */   public String getEmdExBagOvrAllwQual()
/* 2037:     */   {
/* 2038:1974 */     return this.emdExBagOvrAllwQual;
/* 2039:     */   }
/* 2040:     */   
/* 2041:     */   public void setEmdExBagOvrAllwQual(String emdExBagOvrAllwQual)
/* 2042:     */   {
/* 2043:1978 */     this.emdExBagOvrAllwQual = emdExBagOvrAllwQual;
/* 2044:     */   }
/* 2045:     */   
/* 2046:     */   public String getEmdExBagCurrCd()
/* 2047:     */   {
/* 2048:1982 */     return this.emdExBagCurrCd;
/* 2049:     */   }
/* 2050:     */   
/* 2051:     */   public void setEmdExBagCurrCd(String emdExBagCurrCd)
/* 2052:     */   {
/* 2053:1986 */     this.emdExBagCurrCd = emdExBagCurrCd;
/* 2054:     */   }
/* 2055:     */   
/* 2056:     */   public String getEmdExBagRpu()
/* 2057:     */   {
/* 2058:1990 */     return this.emdExBagRpu;
/* 2059:     */   }
/* 2060:     */   
/* 2061:     */   public void setEmdExBagRpu(String emdExBagRpu)
/* 2062:     */   {
/* 2063:1994 */     this.emdExBagRpu = emdExBagRpu;
/* 2064:     */   }
/* 2065:     */   
/* 2066:     */   public String getEmdExBagTotNumExcs()
/* 2067:     */   {
/* 2068:1998 */     return this.emdExBagTotNumExcs;
/* 2069:     */   }
/* 2070:     */   
/* 2071:     */   public void setEmdExBagTotNumExcs(String emdExBagTotNumExcs)
/* 2072:     */   {
/* 2073:2002 */     this.emdExBagTotNumExcs = emdExBagTotNumExcs;
/* 2074:     */   }
/* 2075:     */   
/* 2076:     */   public String getEmdConsuIssInd()
/* 2077:     */   {
/* 2078:2006 */     return this.emdConsuIssInd;
/* 2079:     */   }
/* 2080:     */   
/* 2081:     */   public void setEmdConsuIssInd(String emdConsuIssInd)
/* 2082:     */   {
/* 2083:2010 */     this.emdConsuIssInd = emdConsuIssInd;
/* 2084:     */   }
/* 2085:     */   
/* 2086:     */   public String getEmdNoOfSer()
/* 2087:     */   {
/* 2088:2014 */     return this.emdNoOfSer;
/* 2089:     */   }
/* 2090:     */   
/* 2091:     */   public void setEmdNoOfSer(String emdNoOfSer)
/* 2092:     */   {
/* 2093:2018 */     this.emdNoOfSer = emdNoOfSer;
/* 2094:     */   }
/* 2095:     */   
/* 2096:     */   public String getEmdOppCarr()
/* 2097:     */   {
/* 2098:2022 */     return this.emdOppCarr;
/* 2099:     */   }
/* 2100:     */   
/* 2101:     */   public void setEmdOppCarr(String emdOppCarr)
/* 2102:     */   {
/* 2103:2026 */     this.emdOppCarr = emdOppCarr;
/* 2104:     */   }
/* 2105:     */   
/* 2106:     */   public String getEmdAttGrp()
/* 2107:     */   {
/* 2108:2030 */     return this.emdAttGrp;
/* 2109:     */   }
/* 2110:     */   
/* 2111:     */   public void setEmdAttGrp(String emdAttGrp)
/* 2112:     */   {
/* 2113:2034 */     this.emdAttGrp = emdAttGrp;
/* 2114:     */   }
/* 2115:     */   
/* 2116:     */   public String getEmdAttSubGrp()
/* 2117:     */   {
/* 2118:2038 */     return this.emdAttSubGrp;
/* 2119:     */   }
/* 2120:     */   
/* 2121:     */   public void setEmdAttSubGrp(String emdAttSubGrp)
/* 2122:     */   {
/* 2123:2042 */     this.emdAttSubGrp = emdAttSubGrp;
/* 2124:     */   }
/* 2125:     */   
/* 2126:     */   public String getEmdIndCarrInd()
/* 2127:     */   {
/* 2128:2046 */     return this.emdIndCarrInd;
/* 2129:     */   }
/* 2130:     */   
/* 2131:     */   public void setEmdIndCarrInd(String emdIndCarrInd)
/* 2132:     */   {
/* 2133:2050 */     this.emdIndCarrInd = emdIndCarrInd;
/* 2134:     */   }
/* 2135:     */   
/* 2136:     */   public String getExtdPymtCd()
/* 2137:     */   {
/* 2138:2054 */     return this.ExtdPymtCd;
/* 2139:     */   }
/* 2140:     */   
/* 2141:     */   public void setExtdPymtCd(String extdPymtCd)
/* 2142:     */   {
/* 2143:2058 */     this.ExtdPymtCd = extdPymtCd;
/* 2144:     */   }
/* 2145:     */   
/* 2146:     */   public String getFopTransIdentifier()
/* 2147:     */   {
/* 2148:2062 */     return this.fopTransIdentifier;
/* 2149:     */   }
/* 2150:     */   
/* 2151:     */   public void setFopTransIdentifier(String fopTransIdentifier)
/* 2152:     */   {
/* 2153:2066 */     this.fopTransIdentifier = fopTransIdentifier;
/* 2154:     */   }
/* 2155:     */   
/* 2156:     */   public Boolean getIndicadorNacional()
/* 2157:     */   {
/* 2158:2070 */     return Boolean.valueOf(this.indicadorNacional == null ? false : this.indicadorNacional.booleanValue());
/* 2159:     */   }
/* 2160:     */   
/* 2161:     */   public void setIndicadorNacional(Boolean indicadorNacional)
/* 2162:     */   {
/* 2163:2074 */     this.indicadorNacional = indicadorNacional;
/* 2164:     */   }
/* 2165:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.aerolineas.DetalleTicket
 * JD-Core Version:    0.7.0.1
 */