/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CausaSalidaEmpleado;
/*   7:    */ import com.asinfo.as2.entities.Empleado;
/*   8:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Quincena;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  14:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCausaSalidaEmpleado;
/*  17:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*  18:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  19:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.JsfUtil;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.primefaces.component.datatable.DataTable;
/*  33:    */ import org.primefaces.model.LazyDataModel;
/*  34:    */ import org.primefaces.model.SortOrder;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class SalidaEmpleadoBean
/*  39:    */   extends PageControllerAS2
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = -474225363330588080L;
/*  42:    */   @EJB
/*  43:    */   private ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  44:    */   @EJB
/*  45:    */   private ServicioCausaSalidaEmpleado servicioCausaSalidaEmpleado;
/*  46:    */   @EJB
/*  47:    */   private ServicioEmpresa sevicioEmpresa;
/*  48:    */   @EJB
/*  49:    */   private ServicioQuincena servicioQuincena;
/*  50:    */   private HistoricoEmpleado historicoEmpleado;
/*  51:    */   private LazyDataModel<HistoricoEmpleado> listaHistoricoEmpleado;
/*  52:    */   private List<CausaSalidaEmpleado> listaCausaSalidaEmpleado;
/*  53:    */   private DataTable dtHistoricoEmpleado;
/*  54: 89 */   private boolean indicadorSinQuincena = false;
/*  55:    */   
/*  56:    */   @PostConstruct
/*  57:    */   public void init()
/*  58:    */   {
/*  59: 97 */     Map<String, String> filtersQuincena = new HashMap();
/*  60: 98 */     filtersQuincena.put("indicadorFiniquito", "true");
/*  61: 99 */     filtersQuincena.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  62:    */     
/*  63:101 */     List<Quincena> quincenas = this.servicioQuincena.obtenerListaCombo("", true, filtersQuincena);
/*  64:102 */     if (quincenas.size() == 0)
/*  65:    */     {
/*  66:103 */       this.indicadorSinQuincena = true;
/*  67:104 */       addInfoMessage(getLanguageController().getMensaje("msg_error_quincena_finiquito"));
/*  68:    */     }
/*  69:107 */     this.listaHistoricoEmpleado = new LazyDataModel()
/*  70:    */     {
/*  71:    */       private static final long serialVersionUID = 1L;
/*  72:    */       
/*  73:    */       public List<HistoricoEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  74:    */       {
/*  75:114 */         List<HistoricoEmpleado> lista = new ArrayList();
/*  76:115 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  77:116 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  78:117 */         filters.put("fechaSalida", OperacionEnum.IS_NOT_NULL.toString());
/*  79:118 */         lista = SalidaEmpleadoBean.this.servicioHistoricoEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  80:119 */         SalidaEmpleadoBean.this.listaHistoricoEmpleado.setRowCount(SalidaEmpleadoBean.this.servicioHistoricoEmpleado.contarPorCriterio(filters));
/*  81:    */         
/*  82:121 */         return lista;
/*  83:    */       }
/*  84:    */     };
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void crearHistoricoEmpleado()
/*  88:    */   {
/*  89:134 */     this.historicoEmpleado = new HistoricoEmpleado();
/*  90:135 */     this.historicoEmpleado.setEmpleado(new Empleado());
/*  91:136 */     this.historicoEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  92:137 */     this.historicoEmpleado.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  93:    */     
/*  94:    */ 
/*  95:140 */     this.historicoEmpleado.setFechaSalida(new Date());
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String editar()
/*  99:    */   {
/* 100:150 */     if ((getHistoricoEmpleado() != null) && (getHistoricoEmpleado().getId() != 0))
/* 101:    */     {
/* 102:152 */       HistoricoEmpleado historicoEmpleado = this.servicioHistoricoEmpleado.buscarPorId(getHistoricoEmpleado().getId());
/* 103:153 */       if ((historicoEmpleado.getEstadoFiniquito() == null) || (!historicoEmpleado.getEstadoFiniquito().equals(Estado.CERRADO)))
/* 104:    */       {
/* 105:154 */         setHistoricoEmpleado(this.servicioHistoricoEmpleado.cargarDetalle(getHistoricoEmpleado().getId()));
/* 106:155 */         setEditado(true);
/* 107:    */       }
/* 108:    */       else
/* 109:    */       {
/* 110:157 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 111:    */       }
/* 112:    */     }
/* 113:    */     else
/* 114:    */     {
/* 115:161 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 116:    */     }
/* 117:163 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String guardar()
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:179 */       this.servicioHistoricoEmpleado.guardar(this.historicoEmpleado);
/* 125:180 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 126:181 */       setEditado(false);
/* 127:182 */       limpiar();
/* 128:    */     }
/* 129:    */     catch (ExcepcionAS2Nomina e)
/* 130:    */     {
/* 131:185 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 132:186 */       e.printStackTrace();
/* 133:    */     }
/* 134:    */     catch (AS2Exception e)
/* 135:    */     {
/* 136:188 */       JsfUtil.addErrorMessage(e, "");
/* 137:189 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 138:    */     }
/* 139:    */     catch (ExcepcionAS2 e)
/* 140:    */     {
/* 141:191 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 142:192 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 143:    */     }
/* 144:    */     catch (Exception e)
/* 145:    */     {
/* 146:194 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 147:195 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 148:    */     }
/* 149:197 */     return "";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String eliminar()
/* 153:    */   {
/* 154:    */     try
/* 155:    */     {
/* 156:208 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 157:    */     }
/* 158:    */     catch (Exception e)
/* 159:    */     {
/* 160:210 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 161:211 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 162:    */     }
/* 163:213 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String cargarDatos()
/* 167:    */   {
/* 168:222 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String limpiar()
/* 172:    */   {
/* 173:231 */     crearHistoricoEmpleado();
/* 174:232 */     return "";
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void dateSelect() {}
/* 178:    */   
/* 179:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 180:    */   {
/* 181:254 */     return this.historicoEmpleado;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 185:    */   {
/* 186:264 */     this.historicoEmpleado = historicoEmpleado;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public LazyDataModel<HistoricoEmpleado> getListaHistoricoEmpleado()
/* 190:    */   {
/* 191:273 */     return this.listaHistoricoEmpleado;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaHistoricoEmpleado(LazyDataModel<HistoricoEmpleado> listaHistoricoEmpleado)
/* 195:    */   {
/* 196:283 */     this.listaHistoricoEmpleado = listaHistoricoEmpleado;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public DataTable getDtHistoricoEmpleado()
/* 200:    */   {
/* 201:292 */     return this.dtHistoricoEmpleado;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setDtHistoricoEmpleado(DataTable dtHistoricoEmpleado)
/* 205:    */   {
/* 206:302 */     this.dtHistoricoEmpleado = dtHistoricoEmpleado;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public List<HistoricoEmpleado> autocompletarHistoricoEmpleadoSalida(String consulta)
/* 210:    */   {
/* 211:312 */     Map<String, String> filters = new HashMap();
/* 212:313 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 213:314 */     filters.put("fechaSalida", "null");
/* 214:315 */     return this.servicioHistoricoEmpleado.autocompletarHistoricoEmpleado(consulta, filters);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public List<CausaSalidaEmpleado> getListaCausaSalidaEmpleado()
/* 218:    */   {
/* 219:325 */     if (this.listaCausaSalidaEmpleado == null) {
/* 220:326 */       this.listaCausaSalidaEmpleado = this.servicioCausaSalidaEmpleado.obtenerListaCombo("nombre", true, null);
/* 221:    */     }
/* 222:328 */     return this.listaCausaSalidaEmpleado;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isIndicadorSinQuincena()
/* 226:    */   {
/* 227:332 */     return this.indicadorSinQuincena;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setIndicadorSinQuincena(boolean indicadorSinQuincena)
/* 231:    */   {
/* 232:336 */     this.indicadorSinQuincena = indicadorSinQuincena;
/* 233:    */   }
/* 234:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.SalidaEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */