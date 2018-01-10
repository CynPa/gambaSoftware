/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.GastoDeducibleSRI;
/*   7:    */ import com.asinfo.as2.entities.ImpuestoRentaSRI;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioGastoDeducibleSRI;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  11:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioImpuestoRentaSRI;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Collection;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import javax.faces.component.UIComponent;
/*  24:    */ import javax.faces.context.FacesContext;
/*  25:    */ import javax.faces.context.PartialViewContext;
/*  26:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ import org.primefaces.component.datatable.DataTable;
/*  29:    */ import org.primefaces.context.RequestContext;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class GastoDeducibleSRIBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 3673399085738418127L;
/*  37:    */   @EJB
/*  38:    */   private ServicioGastoDeducibleSRI servicioGastoDeducibleSRI;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpleado servicioEmpleado;
/*  41:    */   @EJB
/*  42:    */   private ServicioImpuestoRentaSRI servicioImpuestoRentaSRI;
/*  43:    */   private Empleado empleado;
/*  44:    */   private GastoDeducibleSRI gastoDeducibleSRI;
/*  45: 70 */   private int anio = FuncionesUtiles.obtenerAnioActual();
/*  46: 71 */   private BigDecimal valorMaximoGastos = BigDecimal.ZERO;
/*  47: 72 */   private String idUpdate = "";
/*  48:    */   private List<GastoDeducibleSRI> listaGastoDeducibleSRI;
/*  49:    */   private DataTable dtGastoDeducibleSRI;
/*  50:    */   
/*  51:    */   public String crear()
/*  52:    */   {
/*  53: 94 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  54: 95 */     return "";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String cargarEmpleado()
/*  58:    */   {
/*  59:102 */     return "";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String editar()
/*  63:    */   {
/*  64:111 */     int id = 0;
/*  65:112 */     if (this.empleado != null) {
/*  66:113 */       id = this.empleado.getIdEmpleado();
/*  67:    */     }
/*  68:115 */     if (this.listaGastoDeducibleSRI != null) {
/*  69:116 */       this.listaGastoDeducibleSRI.clear();
/*  70:    */     }
/*  71:118 */     List<ImpuestoRentaSRI> listaImpuestoRentaSRI = this.servicioImpuestoRentaSRI.obtenerTablaPorAnio(getAnio(), 
/*  72:119 */       AppUtil.getOrganizacion().getIdOrganizacion());
/*  73:120 */     if (listaImpuestoRentaSRI.isEmpty())
/*  74:    */     {
/*  75:121 */       addErrorMessage(getLanguageController().getMensaje("msg_valores_impuesto_renta_no_configurados"));
/*  76:122 */       setValorMaximoGastos(BigDecimal.ZERO);
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:124 */       this.listaGastoDeducibleSRI = this.servicioGastoDeducibleSRI.obtenerListaPorAnio(getAnio(), AppUtil.getOrganizacion().getIdOrganizacion(), id);
/*  81:125 */       setValorMaximoGastos(((ImpuestoRentaSRI)listaImpuestoRentaSRI.get(0)).getHasta());
/*  82:    */     }
/*  83:127 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public List<Empleado> autocompletarEmpleado(String consulta)
/*  87:    */   {
/*  88:132 */     Map<String, String> filters = new HashMap();
/*  89:133 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  90:134 */     filters.put("empresa.activo", "true");
/*  91:135 */     filters.put("filtro", "%" + consulta + "%");
/*  92:136 */     return this.servicioEmpleado.obtenerListaCombo("", true, filters);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String guardar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:150 */       for (GastoDeducibleSRI gastoDeducibleSRI : this.listaGastoDeducibleSRI)
/* 100:    */       {
/* 101:151 */         gastoDeducibleSRI.setAnio(getAnio());
/* 102:152 */         this.servicioGastoDeducibleSRI.guardar(gastoDeducibleSRI);
/* 103:    */       }
/* 104:154 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 105:155 */       setEditado(false);
/* 106:156 */       limpiar();
/* 107:    */     }
/* 108:    */     catch (Exception e)
/* 109:    */     {
/* 110:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 111:159 */       e.printStackTrace();
/* 112:160 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 113:    */     }
/* 114:162 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String eliminar()
/* 118:    */   {
/* 119:    */     try
/* 120:    */     {
/* 121:172 */       this.servicioGastoDeducibleSRI.eliminar(this.gastoDeducibleSRI);
/* 122:173 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 123:    */     }
/* 124:    */     catch (Exception e)
/* 125:    */     {
/* 126:175 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 127:176 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 128:    */     }
/* 129:178 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String cargarDatos()
/* 133:    */   {
/* 134:187 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String limpiar()
/* 138:    */   {
/* 139:196 */     this.listaGastoDeducibleSRI = new ArrayList();
/* 140:197 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void calcularValorTotalDeducible(AjaxBehaviorEvent event)
/* 144:    */   {
/* 145:202 */     String update = (String)event.getComponent().getAttributes().get("idUpdate");
/* 146:203 */     GastoDeducibleSRI gastoDeducibleSRI = (GastoDeducibleSRI)event.getComponent().getAttributes().get("gastoDeducibleSRI");
/* 147:    */     
/* 148:205 */     BigDecimal multiploEmpleado = new BigDecimal(1.3D);
/* 149:206 */     if (gastoDeducibleSRI.getEmpleado().isDiscapacitado()) {
/* 150:207 */       multiploEmpleado = new BigDecimal(3);
/* 151:208 */     } else if (gastoDeducibleSRI.getEmpleado().isTerceraEdad()) {
/* 152:209 */       multiploEmpleado = new BigDecimal(2);
/* 153:    */     }
/* 154:213 */     BigDecimal totalGasto = gastoDeducibleSRI.getValorAlimentacion().add(gastoDeducibleSRI.getValorEducacion()).add(gastoDeducibleSRI.getValorSalud()).add(gastoDeducibleSRI.getValorVestimenta()).add(gastoDeducibleSRI.getValorVivienda());
/* 155:215 */     if (totalGasto.compareTo(getValorMaximoGastos().multiply(multiploEmpleado)) == 1)
/* 156:    */     {
/* 157:216 */       addErrorMessage(getLanguageController().getMensaje("msg_base_minima_superada"));
/* 158:217 */       if (update.equals("idAlimentacion")) {
/* 159:218 */         gastoDeducibleSRI.setValorAlimentacion(BigDecimal.ZERO);
/* 160:219 */       } else if (update.equals("idEducacion")) {
/* 161:220 */         gastoDeducibleSRI.setValorEducacion(BigDecimal.ZERO);
/* 162:221 */       } else if (update.equals("idSalud")) {
/* 163:222 */         gastoDeducibleSRI.setValorSalud(BigDecimal.ZERO);
/* 164:223 */       } else if (update.equals("idVestimenta")) {
/* 165:224 */         gastoDeducibleSRI.setValorVestimenta(BigDecimal.ZERO);
/* 166:225 */       } else if (update.equals("idVivienda")) {
/* 167:226 */         gastoDeducibleSRI.setValorVivienda(BigDecimal.ZERO);
/* 168:    */       }
/* 169:228 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
/* 170:229 */       RequestContext.getCurrentInstance().update(update);
/* 171:    */     }
/* 172:231 */     gastoDeducibleSRI.setTotalGastosDeducibles(gastoDeducibleSRI.getValorAlimentacion().add(gastoDeducibleSRI.getValorEducacion())
/* 173:232 */       .add(gastoDeducibleSRI.getValorSalud()).add(gastoDeducibleSRI.getValorVestimenta()).add(gastoDeducibleSRI.getValorVivienda()));
/* 174:    */   }
/* 175:    */   
/* 176:    */   public GastoDeducibleSRI getGastoDeducibleSRI()
/* 177:    */   {
/* 178:244 */     if (this.gastoDeducibleSRI == null) {
/* 179:245 */       this.gastoDeducibleSRI = new GastoDeducibleSRI();
/* 180:    */     }
/* 181:247 */     return this.gastoDeducibleSRI;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setGastoDeducibleSRI(GastoDeducibleSRI gastoDeducibleSRI)
/* 185:    */   {
/* 186:257 */     this.gastoDeducibleSRI = gastoDeducibleSRI;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<GastoDeducibleSRI> getListaGastoDeducibleSRI()
/* 190:    */   {
/* 191:266 */     if (this.listaGastoDeducibleSRI == null) {
/* 192:267 */       this.listaGastoDeducibleSRI = new ArrayList();
/* 193:    */     }
/* 194:269 */     return this.listaGastoDeducibleSRI;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setListaGastoDeducibleSRI(List<GastoDeducibleSRI> listaGastoDeducibleSRI)
/* 198:    */   {
/* 199:279 */     this.listaGastoDeducibleSRI = listaGastoDeducibleSRI;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public DataTable getDtGastoDeducibleSRI()
/* 203:    */   {
/* 204:288 */     return this.dtGastoDeducibleSRI;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setDtGastoDeducibleSRI(DataTable dtGastoDeducibleSRI)
/* 208:    */   {
/* 209:298 */     this.dtGastoDeducibleSRI = dtGastoDeducibleSRI;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int getAnio()
/* 213:    */   {
/* 214:307 */     return this.anio;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setAnio(int anio)
/* 218:    */   {
/* 219:317 */     this.anio = anio;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public Empleado getEmpleado()
/* 223:    */   {
/* 224:321 */     return this.empleado;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setEmpleado(Empleado empleado)
/* 228:    */   {
/* 229:325 */     this.empleado = empleado;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public BigDecimal getValorMaximoGastos()
/* 233:    */   {
/* 234:329 */     return this.valorMaximoGastos;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setValorMaximoGastos(BigDecimal valorMaximoGastos)
/* 238:    */   {
/* 239:333 */     this.valorMaximoGastos = valorMaximoGastos;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String getIdUpdate()
/* 243:    */   {
/* 244:337 */     return this.idUpdate;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setIdUpdate(String idUpdate)
/* 248:    */   {
/* 249:341 */     this.idUpdate = idUpdate;
/* 250:    */   }
/* 251:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.GastoDeducibleSRIBean
 * JD-Core Version:    0.7.0.1
 */