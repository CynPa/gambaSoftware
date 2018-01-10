/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="motivo_ajuste_unidad_manejo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class MotivoAjusteUnidadManejo
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -6592981785418453353L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="motivo_ajuste_unidad_manejo", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="motivo_ajuste_unidad_manejo")
/*  22:    */   @Column(name="id_motivo_ajuste_unidad_manejo")
/*  23:    */   private int idMotivoAjusteUnidadManejo;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", nullable=false, length=10)
/*  29:    */   @NotNull
/*  30:    */   @Size(min=2, max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", nullable=false, length=50)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 70 */     return this.idMotivoAjusteUnidadManejo;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdMotivoAjusteUnidadManejo()
/*  50:    */   {
/*  51: 77 */     return this.idMotivoAjusteUnidadManejo;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdMotivoAjusteUnidadManejo(int idMotivoAjusteUnidadManejo)
/*  55:    */   {
/*  56: 85 */     this.idMotivoAjusteUnidadManejo = idMotivoAjusteUnidadManejo;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdOrganizacion()
/*  60:    */   {
/*  61: 92 */     return this.idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdOrganizacion(int idOrganizacion)
/*  65:    */   {
/*  66:100 */     this.idOrganizacion = idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdSucursal()
/*  70:    */   {
/*  71:107 */     return this.idSucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdSucursal(int idSucursal)
/*  75:    */   {
/*  76:115 */     this.idSucursal = idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getCodigo()
/*  80:    */   {
/*  81:122 */     return this.codigo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setCodigo(String codigo)
/*  85:    */   {
/*  86:130 */     this.codigo = codigo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getNombre()
/*  90:    */   {
/*  91:137 */     return this.nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setNombre(String nombre)
/*  95:    */   {
/*  96:145 */     this.nombre = nombre;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getDescripcion()
/* 100:    */   {
/* 101:152 */     return this.descripcion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setDescripcion(String descripcion)
/* 105:    */   {
/* 106:160 */     this.descripcion = descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isActivo()
/* 110:    */   {
/* 111:167 */     return this.activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setActivo(boolean activo)
/* 115:    */   {
/* 116:175 */     this.activo = activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isPredeterminado()
/* 120:    */   {
/* 121:182 */     return this.predeterminado;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setPredeterminado(boolean predeterminado)
/* 125:    */   {
/* 126:190 */     this.predeterminado = predeterminado;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MotivoAjusteUnidadManejo
 * JD-Core Version:    0.7.0.1
 */