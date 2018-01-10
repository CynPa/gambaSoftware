/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Transportista;
/*   7:    */ import com.asinfo.as2.entities.Zona;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Calendar;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.context.RequestContext;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class ActualizacionTransportistaBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private ServicioTransportista servicioTransportista;
/*  36:    */   @EJB
/*  37:    */   private ServicioZona servicioZona;
/*  38:    */   private Date fecha;
/*  39:    */   private Transportista transportista;
/*  40:    */   private Zona zona;
/*  41: 59 */   private boolean actualizarCliente = false;
/*  42:    */   private List<Transportista> listaTransportista;
/*  43:    */   private List<Zona> zonasSelecionadas;
/*  44:    */   private List<Zona> listaZona;
/*  45:    */   private DataTable dtZona;
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 73 */     Calendar fechaCalendar = Calendar.getInstance();
/*  51: 74 */     fechaCalendar.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  52: 75 */     this.fecha = fechaCalendar.getTime();
/*  53: 76 */     this.fecha = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String editar()
/*  57:    */   {
/*  58: 82 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String guardar()
/*  62:    */   {
/*  63: 88 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String eliminar()
/*  67:    */   {
/*  68: 94 */     return null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String limpiar()
/*  72:    */   {
/*  73:100 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String cargarDatos()
/*  77:    */   {
/*  78:106 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Date getFecha()
/*  82:    */   {
/*  83:110 */     return this.fecha;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setFecha(Date fecha)
/*  87:    */   {
/*  88:114 */     this.fecha = fecha;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Transportista getTransportista()
/*  92:    */   {
/*  93:118 */     return this.transportista;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setTransportista(Transportista transportista)
/*  97:    */   {
/*  98:122 */     this.transportista = transportista;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Zona getZona()
/* 102:    */   {
/* 103:126 */     return this.zona;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setZona(Zona zona)
/* 107:    */   {
/* 108:130 */     this.zona = zona;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public DataTable getDtZona()
/* 112:    */   {
/* 113:134 */     return this.dtZona;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List<Zona> getListaZona()
/* 117:    */   {
/* 118:138 */     return this.listaZona;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setListaZona(List<Zona> listaZona)
/* 122:    */   {
/* 123:142 */     this.listaZona = listaZona;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setDtZona(DataTable dtZona)
/* 127:    */   {
/* 128:146 */     this.dtZona = dtZona;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<Zona> getZonasSelecionadas()
/* 132:    */   {
/* 133:150 */     if (this.zonasSelecionadas == null) {
/* 134:151 */       this.zonasSelecionadas = new ArrayList();
/* 135:    */     }
/* 136:153 */     return this.zonasSelecionadas;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setZonasSelecionadas(List<Zona> zonasSelecionadas)
/* 140:    */   {
/* 141:157 */     this.zonasSelecionadas = zonasSelecionadas;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<Transportista> getListaTransportista()
/* 145:    */   {
/* 146:161 */     HashMap<String, String> filters = new HashMap();
/* 147:162 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 148:163 */     if (this.listaTransportista == null) {
/* 149:164 */       this.listaTransportista = this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 150:    */     }
/* 151:167 */     return this.listaTransportista;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<Zona> autoCompletarZona(String consulta)
/* 155:    */   {
/* 156:171 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 157:172 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 158:173 */       filters.put("nombre", "%" + consulta + "%");
/* 159:    */     }
/* 160:175 */     return this.servicioZona.obtenerListaCombo("nombre", true, filters);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void cargarZona()
/* 164:    */   {
/* 165:179 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 166:180 */     this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, filters);
/* 167:181 */     RequestContext context = RequestContext.getCurrentInstance();
/* 168:182 */     context.execute("PF('cerrarDialog').show();");
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void procesar()
/* 172:    */     throws AS2Exception, ExcepcionAS2
/* 173:    */   {
/* 174:    */     try
/* 175:    */     {
/* 176:187 */       this.servicioTransportista.actuzalizaTransportista(this.fecha, this.zonasSelecionadas, this.transportista, this.actualizarCliente);
/* 177:188 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 178:    */     }
/* 179:    */     catch (AS2Exception e)
/* 180:    */     {
/* 181:190 */       JsfUtil.addErrorMessage(e, "");
/* 182:    */     }
/* 183:    */     catch (Exception e)
/* 184:    */     {
/* 185:192 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 186:193 */       e.printStackTrace();
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isActualizarCliente()
/* 191:    */   {
/* 192:198 */     return this.actualizarCliente;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setActualizarCliente(boolean actualizarCliente)
/* 196:    */   {
/* 197:202 */     this.actualizarCliente = actualizarCliente;
/* 198:    */   }
/* 199:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.ActualizacionTransportistaBean
 * JD-Core Version:    0.7.0.1
 */