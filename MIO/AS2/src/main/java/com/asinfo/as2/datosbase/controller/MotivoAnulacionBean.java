/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioMotivoAnulacion;
/*   6:    */ import com.asinfo.as2.entities.MotivoAnulacion;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class MotivoAnulacionBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioMotivoAnulacion servicioMotivoAnulacion;
/*  30:    */   private MotivoAnulacion motivoAnulacion;
/*  31:    */   private LazyDataModel<MotivoAnulacion> listaMotivoAnulacion;
/*  32:    */   private DataTable dtMotivoAnulacion;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 75 */     crearMotivoAnulacion();
/*  38: 76 */     this.listaMotivoAnulacion = new LazyDataModel()
/*  39:    */     {
/*  40:    */       private static final long serialVersionUID = 1L;
/*  41:    */       
/*  42:    */       public List<MotivoAnulacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  43:    */       {
/*  44: 83 */         List<MotivoAnulacion> lista = new ArrayList();
/*  45:    */         
/*  46: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 87 */         lista = MotivoAnulacionBean.this.servicioMotivoAnulacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  49: 88 */         MotivoAnulacionBean.this.listaMotivoAnulacion.setRowCount(MotivoAnulacionBean.this.servicioMotivoAnulacion.contarPorCriterio(filters));
/*  50:    */         
/*  51: 90 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String crearMotivoAnulacion()
/*  57:    */   {
/*  58:102 */     this.motivoAnulacion = new MotivoAnulacion();
/*  59:103 */     this.motivoAnulacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  60:104 */     this.motivoAnulacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:105 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String guardar()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:116 */       this.servicioMotivoAnulacion.guardar(this.motivoAnulacion);
/*  69:117 */       setEditado(false);
/*  70:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  71:119 */       limpiar();
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  76:122 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  77:    */     }
/*  78:124 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String eliminar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:135 */       this.servicioMotivoAnulacion.eliminar(this.motivoAnulacion);
/*  86:136 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  87:137 */       limpiar();
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  92:140 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  93:    */     }
/*  94:142 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String cargarDatos()
/*  98:    */   {
/*  99:152 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String editar()
/* 103:    */   {
/* 104:162 */     if (getMotivoAnulacion().getId() > 0)
/* 105:    */     {
/* 106:163 */       this.motivoAnulacion = this.servicioMotivoAnulacion.buscarPorId(getMotivoAnulacion().getId());
/* 107:164 */       setEditado(true);
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:166 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 112:    */     }
/* 113:169 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String limpiar()
/* 117:    */   {
/* 118:179 */     this.motivoAnulacion = null;
/* 119:180 */     crearMotivoAnulacion();
/* 120:181 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public MotivoAnulacion getMotivoAnulacion()
/* 124:    */   {
/* 125:189 */     if (this.motivoAnulacion == null) {
/* 126:190 */       crearMotivoAnulacion();
/* 127:    */     }
/* 128:192 */     return this.motivoAnulacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setMotivoAnulacion(MotivoAnulacion motivoAnulacion)
/* 132:    */   {
/* 133:196 */     this.motivoAnulacion = motivoAnulacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtMotivoAnulacion()
/* 137:    */   {
/* 138:200 */     return this.dtMotivoAnulacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtMotivoAnulacion(DataTable dtMotivoAnulacion)
/* 142:    */   {
/* 143:204 */     this.dtMotivoAnulacion = dtMotivoAnulacion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public ServicioMotivoAnulacion getServicioMotivoAnulacion()
/* 147:    */   {
/* 148:208 */     return this.servicioMotivoAnulacion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public LazyDataModel<MotivoAnulacion> getListaMotivoAnulacion()
/* 152:    */   {
/* 153:212 */     return this.listaMotivoAnulacion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setListaMotivoAnulacion(LazyDataModel<MotivoAnulacion> listaMotivoAnulacion)
/* 157:    */   {
/* 158:216 */     this.listaMotivoAnulacion = listaMotivoAnulacion;
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.MotivoAnulacionBean
 * JD-Core Version:    0.7.0.1
 */