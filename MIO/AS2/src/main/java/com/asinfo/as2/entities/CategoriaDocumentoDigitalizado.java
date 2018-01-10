/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.persistence.Transient;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="categoria_documento_digitalizado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  19:    */ public class CategoriaDocumentoDigitalizado
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = -1133788217704360695L;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="categoria_documento_digitalizado", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_documento_digitalizado")
/*  31:    */   @Column(name="id_categoria_documento_digitalizado", unique=true, nullable=false)
/*  32:    */   private int idCategoriaDocumentoDigitalizado;
/*  33:    */   @Column(name="nombre", nullable=false, length=50)
/*  34:    */   @Size(min=2, max=50)
/*  35:    */   @NotNull
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="codigo", nullable=true, length=20)
/*  38:    */   @Size(max=20)
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="descripcion", nullable=true, length=200)
/*  41:    */   @Size(max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44:    */   private boolean activo;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Transient
/*  48:    */   private List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado;
/*  49:    */   
/*  50:    */   public int getId()
/*  51:    */   {
/*  52: 66 */     return this.idCategoriaDocumentoDigitalizado;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getNombre()
/*  56:    */   {
/*  57: 70 */     return this.nombre;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setNombre(String nombre)
/*  61:    */   {
/*  62: 74 */     this.nombre = nombre;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdOrganizacion()
/*  66:    */   {
/*  67: 78 */     return this.idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdOrganizacion(int idOrganizacion)
/*  71:    */   {
/*  72: 82 */     this.idOrganizacion = idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdSucursal()
/*  76:    */   {
/*  77: 86 */     return this.idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdSucursal(int idSucursal)
/*  81:    */   {
/*  82: 90 */     this.idSucursal = idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdCategoriaDocumentoDigitalizado()
/*  86:    */   {
/*  87: 94 */     return this.idCategoriaDocumentoDigitalizado;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdCategoriaDocumentoDigitalizado(int idCategoriaDocumentoDigitalizado)
/*  91:    */   {
/*  92: 99 */     this.idCategoriaDocumentoDigitalizado = idCategoriaDocumentoDigitalizado;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getCodigo()
/*  96:    */   {
/*  97:103 */     return this.codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setCodigo(String codigo)
/* 101:    */   {
/* 102:107 */     this.codigo = codigo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<DetalleDocumentoDigitalizado> getListaDetalleDocumentoDigitalizadoEmpleado()
/* 106:    */   {
/* 107:111 */     if (this.listaDetalleDocumentoDigitalizadoEmpleado == null) {
/* 108:112 */       this.listaDetalleDocumentoDigitalizadoEmpleado = new ArrayList();
/* 109:    */     }
/* 110:114 */     return this.listaDetalleDocumentoDigitalizadoEmpleado;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setListaDetalleDocumentoDigitalizadoEmpleado(List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado)
/* 114:    */   {
/* 115:119 */     this.listaDetalleDocumentoDigitalizadoEmpleado = listaDetalleDocumentoDigitalizadoEmpleado;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getDescripcion()
/* 119:    */   {
/* 120:123 */     return this.descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDescripcion(String descripcion)
/* 124:    */   {
/* 125:127 */     this.descripcion = descripcion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isActivo()
/* 129:    */   {
/* 130:131 */     return this.activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setActivo(boolean activo)
/* 134:    */   {
/* 135:135 */     this.activo = activo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isPredeterminado()
/* 139:    */   {
/* 140:139 */     return this.predeterminado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPredeterminado(boolean predeterminado)
/* 144:    */   {
/* 145:143 */     this.predeterminado = predeterminado;
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaDocumentoDigitalizado
 * JD-Core Version:    0.7.0.1
 */