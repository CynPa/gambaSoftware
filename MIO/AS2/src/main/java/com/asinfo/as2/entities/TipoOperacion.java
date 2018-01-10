/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.Size;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="tipo_operacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  14:    */ public class TipoOperacion
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1L;
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="tipo_operacion", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_operacion")
/*  21:    */   @Column(name="id_tipo_operacion")
/*  22:    */   private int idTipoOperacion;
/*  23:    */   @Column(name="id_organizacion", nullable=false)
/*  24:    */   private int idOrganizacion;
/*  25:    */   @Column(name="id_sucursal", nullable=false)
/*  26:    */   private int idSucursal;
/*  27:    */   @Column(name="codigo", nullable=false)
/*  28:    */   @Size(min=2, max=10)
/*  29:    */   private String codigo;
/*  30:    */   @Column(name="nombre", nullable=false, length=50)
/*  31:    */   @Size(min=2, max=50)
/*  32:    */   private String nombre;
/*  33:    */   @Column(name="descripcion", nullable=true)
/*  34:    */   @Size(max=200)
/*  35:    */   private String descripcion;
/*  36:    */   @Column(name="activo", nullable=false)
/*  37:    */   private boolean activo;
/*  38:    */   @Column(name="predeterminado", nullable=false)
/*  39:    */   private boolean predeterminado;
/*  40:    */   
/*  41:    */   public int getIdTipoOperacion()
/*  42:    */   {
/*  43: 90 */     return this.idTipoOperacion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdTipoOperacion(int idTipoOperacion)
/*  47:    */   {
/*  48:100 */     this.idTipoOperacion = idTipoOperacion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdOrganizacion()
/*  52:    */   {
/*  53:109 */     return this.idOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdOrganizacion(int idOrganizacion)
/*  57:    */   {
/*  58:119 */     this.idOrganizacion = idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdSucursal()
/*  62:    */   {
/*  63:128 */     return this.idSucursal;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdSucursal(int idSucursal)
/*  67:    */   {
/*  68:138 */     this.idSucursal = idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getCodigo()
/*  72:    */   {
/*  73:147 */     return this.codigo;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setCodigo(String codigo)
/*  77:    */   {
/*  78:157 */     this.codigo = codigo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getNombre()
/*  82:    */   {
/*  83:166 */     return this.nombre;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setNombre(String nombre)
/*  87:    */   {
/*  88:176 */     this.nombre = nombre;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getDescripcion()
/*  92:    */   {
/*  93:185 */     return this.descripcion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setDescripcion(String descripcion)
/*  97:    */   {
/*  98:195 */     this.descripcion = descripcion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isActivo()
/* 102:    */   {
/* 103:204 */     return this.activo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setActivo(boolean activo)
/* 107:    */   {
/* 108:214 */     this.activo = activo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isPredeterminado()
/* 112:    */   {
/* 113:223 */     return this.predeterminado;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setPredeterminado(boolean predeterminado)
/* 117:    */   {
/* 118:233 */     this.predeterminado = predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getId()
/* 122:    */   {
/* 123:243 */     return this.idTipoOperacion;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoOperacion
 * JD-Core Version:    0.7.0.1
 */