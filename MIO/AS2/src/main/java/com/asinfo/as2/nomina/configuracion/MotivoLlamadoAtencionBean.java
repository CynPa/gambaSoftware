/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.MotivoLlamadoAtencion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.enumeraciones.VariablesMotivosLlamadosAtencion;
/*   9:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioMotivoLlamadoAtencion;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTable;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class MotivoLlamadoAtencionBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 5562328087712294452L;
/*  29:    */   @EJB
/*  30:    */   private ServicioMotivoLlamadoAtencion servicioMotivoLlamadoAtencion;
/*  31:    */   private MotivoLlamadoAtencion motivoLlamadoAtencion;
/*  32:    */   private LazyDataModel<MotivoLlamadoAtencion> listaMotivoLlamadoAtencion;
/*  33:    */   private DataTable dtMotivoLlamadoAtencion;
/*  34:    */   private List<String> listaVariablesMotivosLlamadoAtencion;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 49 */     this.listaMotivoLlamadoAtencion = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<MotivoLlamadoAtencion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 59 */         List<MotivoLlamadoAtencion> lista = new ArrayList();
/*  46: 60 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 62 */         filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/*  49: 63 */         lista = MotivoLlamadoAtencionBean.this.servicioMotivoLlamadoAtencion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  50:    */         
/*  51: 65 */         MotivoLlamadoAtencionBean.this.listaMotivoLlamadoAtencion.setRowCount(MotivoLlamadoAtencionBean.this.servicioMotivoLlamadoAtencion.contarPorCriterio(filters));
/*  52:    */         
/*  53: 67 */         return lista;
/*  54:    */       }
/*  55:    */     };
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String editar()
/*  59:    */   {
/*  60: 74 */     if ((getMotivoLlamadoAtencion() != null) && (getMotivoLlamadoAtencion().getIdMotivoLlamadoAtencion() != 0)) {
/*  61: 75 */       setEditado(true);
/*  62:    */     } else {
/*  63: 77 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  64:    */     }
/*  65: 79 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String guardar()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 85 */       this.motivoLlamadoAtencion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  73: 86 */       this.motivoLlamadoAtencion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  74: 87 */       this.servicioMotivoLlamadoAtencion.guardar(this.motivoLlamadoAtencion);
/*  75: 88 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  76: 89 */       setEditado(false);
/*  77: 90 */       limpiar();
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81: 92 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  82: 93 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  83:    */     }
/*  84: 95 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String eliminar()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:101 */       this.servicioMotivoLlamadoAtencion.eliminar(this.motivoLlamadoAtencion);
/*  92:102 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  97:105 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  98:    */     }
/*  99:107 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String limpiar()
/* 103:    */   {
/* 104:112 */     this.motivoLlamadoAtencion = new MotivoLlamadoAtencion();
/* 105:113 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String cargarDatos()
/* 109:    */   {
/* 110:119 */     return null;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public MotivoLlamadoAtencion getMotivoLlamadoAtencion()
/* 114:    */   {
/* 115:123 */     return this.motivoLlamadoAtencion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setMotivoLlamadoAtencion(MotivoLlamadoAtencion motivoLlamadoAtencion)
/* 119:    */   {
/* 120:127 */     this.motivoLlamadoAtencion = motivoLlamadoAtencion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public LazyDataModel<MotivoLlamadoAtencion> getListaMotivoLlamadoAtencion()
/* 124:    */   {
/* 125:131 */     return this.listaMotivoLlamadoAtencion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setListaMotivoLlamadoAtencion(LazyDataModel<MotivoLlamadoAtencion> listaMotivoLlamadoAtencion)
/* 129:    */   {
/* 130:136 */     this.listaMotivoLlamadoAtencion = listaMotivoLlamadoAtencion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public DataTable getDtMotivoLlamadoAtencion()
/* 134:    */   {
/* 135:140 */     return this.dtMotivoLlamadoAtencion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setDtMotivoLlamadoAtencion(DataTable dtMotivoLlamadoAtencion)
/* 139:    */   {
/* 140:144 */     this.dtMotivoLlamadoAtencion = dtMotivoLlamadoAtencion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<String> getListaVariablesMotivosLlamadoAtencion()
/* 144:    */   {
/* 145:148 */     if (this.listaVariablesMotivosLlamadoAtencion == null)
/* 146:    */     {
/* 147:149 */       this.listaVariablesMotivosLlamadoAtencion = new ArrayList();
/* 148:150 */       for (VariablesMotivosLlamadosAtencion variablesMotivo : VariablesMotivosLlamadosAtencion.values()) {
/* 149:151 */         this.listaVariablesMotivosLlamadoAtencion.add(variablesMotivo.getVariable());
/* 150:    */       }
/* 151:    */     }
/* 152:154 */     return this.listaVariablesMotivosLlamadoAtencion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setListaVariablesMotivosLlamadoAtencion(List<String> listaVariablesMotivosLlamadoAtencion)
/* 156:    */   {
/* 157:158 */     this.listaVariablesMotivosLlamadoAtencion = listaVariablesMotivosLlamadoAtencion;
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.MotivoLlamadoAtencionBean
 * JD-Core Version:    0.7.0.1
 */