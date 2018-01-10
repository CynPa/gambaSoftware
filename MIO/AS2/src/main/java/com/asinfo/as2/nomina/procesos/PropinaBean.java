/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.dao.PagoRolDao;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.PagoRol;
/*   9:    */ import com.asinfo.as2.entities.Propina;
/*  10:    */ import com.asinfo.as2.entities.Quincena;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  13:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  14:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPropina;
/*  15:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.JsfUtil;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import javax.faces.model.SelectItem;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.primefaces.component.datatable.DataTable;
/*  31:    */ import org.primefaces.model.LazyDataModel;
/*  32:    */ import org.primefaces.model.SortOrder;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class PropinaBean
/*  37:    */   extends PageControllerAS2
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 1L;
/*  40:    */   @EJB
/*  41:    */   private ServicioPropina servicioPropina;
/*  42:    */   @EJB
/*  43:    */   private ServicioPagoRol servicioPagoRol;
/*  44:    */   @EJB
/*  45:    */   private ServicioRubroEmpleado servicioRubroEmpleado;
/*  46:    */   @EJB
/*  47:    */   private PagoRolDao pagoRolDao;
/*  48:    */   private Propina propina;
/*  49:    */   private LazyDataModel<Propina> listaPropina;
/*  50:    */   private DataTable dtPropina;
/*  51:    */   private List<SelectItem> listaPagoRol;
/*  52:    */   private List<Empleado> listaEmpleadosAsignacionRubro;
/*  53:    */   private DataTable dtEmpleadoAsignacionRubro;
/*  54:    */   
/*  55:    */   @PostConstruct
/*  56:    */   public void init()
/*  57:    */   {
/*  58: 71 */     this.listaPropina = new LazyDataModel()
/*  59:    */     {
/*  60:    */       private static final long serialVersionUID = 1L;
/*  61:    */       
/*  62:    */       public List<Propina> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  63:    */       {
/*  64: 78 */         List<Propina> lista = new ArrayList();
/*  65: 79 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  66:    */         
/*  67: 81 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  68: 82 */         lista = PropinaBean.this.servicioPropina.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  69:    */         
/*  70: 84 */         PropinaBean.this.listaPropina.setRowCount(PropinaBean.this.servicioPropina.contarPorCriterio(filters));
/*  71:    */         
/*  72: 86 */         return lista;
/*  73:    */       }
/*  74:    */     };
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String editar()
/*  78:    */   {
/*  79: 94 */     this.propina = this.servicioPropina.cargarDetalle(this.propina.getIdPropina());
/*  80: 95 */     setEditado(true);
/*  81:    */     
/*  82: 97 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String guardar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:104 */       this.servicioPropina.guardar(getPropina());
/*  90:105 */       setEditado(false);
/*  91:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:108 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  96:109 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  97:    */     }
/*  98:112 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String eliminar()
/* 102:    */   {
/* 103:118 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String limpiar()
/* 107:    */   {
/* 108:123 */     crearPropina();
/* 109:124 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String cargarDatos()
/* 113:    */   {
/* 114:130 */     return null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void crearPropina()
/* 118:    */   {
/* 119:134 */     this.propina = new Propina();
/* 120:135 */     this.propina.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 121:136 */     this.propina.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 122:137 */     this.propina.setPagoRol(null);
/* 123:138 */     this.propina.setPagoRolDiasTrabajados(null);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void procesarPropinaListener(Propina propina)
/* 127:    */   {
/* 128:    */     try
/* 129:    */     {
/* 130:146 */       this.servicioPagoRol.procesarPropina(propina.getPagoRol(), propina.getPagoRolDiasTrabajados(), propina.getValor());
/* 131:147 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 132:    */     }
/* 133:    */     catch (AS2Exception e)
/* 134:    */     {
/* 135:150 */       JsfUtil.addErrorMessage(e, "");
/* 136:151 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 137:152 */       e.printStackTrace();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void guardarEmpleadoAsignacionRubro()
/* 142:    */   {
/* 143:159 */     this.servicioRubroEmpleado.guardarEmpleadoAsignacionRubro(this.listaEmpleadosAsignacionRubro, this.propina.getPagoRol());
/* 144:160 */     addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void cargarRol()
/* 148:    */   {
/* 149:164 */     int mes = this.propina.getPagoRol().getMes();
/* 150:165 */     int anio = this.propina.getPagoRol().getAnio();
/* 151:166 */     Date fecha = FuncionesUtiles.getFecha(1, mes - 1, anio);
/* 152:167 */     Date fechaRolAntigua = FuncionesUtiles.getFechaFinMes(fecha);
/* 153:168 */     PagoRol pagoRolAntiguos = this.pagoRolDao.obtenerPagoRolPorFecha(AppUtil.getOrganizacion().getIdOrganizacion(), fechaRolAntigua);
/* 154:169 */     this.propina.setPagoRolDiasTrabajados(pagoRolAntiguos);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Propina getPropina()
/* 158:    */   {
/* 159:173 */     return this.propina;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setPropina(Propina propina)
/* 163:    */   {
/* 164:177 */     this.propina = propina;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public LazyDataModel<Propina> getListaPropina()
/* 168:    */   {
/* 169:181 */     return this.listaPropina;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setListaPropina(LazyDataModel<Propina> listaPropina)
/* 173:    */   {
/* 174:185 */     this.listaPropina = listaPropina;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public DataTable getDtPropina()
/* 178:    */   {
/* 179:189 */     return this.dtPropina;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setDtPropina(DataTable dtPropina)
/* 183:    */   {
/* 184:193 */     this.dtPropina = dtPropina;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<SelectItem> getListaPagoRol()
/* 188:    */   {
/* 189:198 */     List<PagoRol> lista = new ArrayList();
/* 190:199 */     Map<String, String> filters = new HashMap();
/* 191:200 */     filters.put("indicadorFiniquito", "false");
/* 192:    */     
/* 193:202 */     lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 194:203 */     if (this.listaPagoRol == null)
/* 195:    */     {
/* 196:204 */       this.listaPagoRol = new ArrayList();
/* 197:205 */       for (PagoRol pagoRol : lista)
/* 198:    */       {
/* 199:211 */         String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 200:212 */         SelectItem item = new SelectItem(pagoRol, label);
/* 201:213 */         this.listaPagoRol.add(item);
/* 202:    */       }
/* 203:    */     }
/* 204:216 */     return this.listaPagoRol;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaPagoRol(List<SelectItem> listaPagoRol)
/* 208:    */   {
/* 209:221 */     this.listaPagoRol = listaPagoRol;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Empleado> getListaEmpleadosAsignacionRubro()
/* 213:    */   {
/* 214:225 */     return this.listaEmpleadosAsignacionRubro == null ? (this.listaEmpleadosAsignacionRubro = new ArrayList()) : this.listaEmpleadosAsignacionRubro;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setListaEmpleadosAsignacionRubro(List<Empleado> listaEmpleadosAsignacionRubro)
/* 218:    */   {
/* 219:229 */     this.listaEmpleadosAsignacionRubro = listaEmpleadosAsignacionRubro;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public DataTable getDtEmpleadoAsignacionRubro()
/* 223:    */   {
/* 224:233 */     return this.dtEmpleadoAsignacionRubro;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setDtEmpleadoAsignacionRubro(DataTable dtEmpleadoAsignacionRubro)
/* 228:    */   {
/* 229:237 */     this.dtEmpleadoAsignacionRubro = dtEmpleadoAsignacionRubro;
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.PropinaBean
 * JD-Core Version:    0.7.0.1
 */