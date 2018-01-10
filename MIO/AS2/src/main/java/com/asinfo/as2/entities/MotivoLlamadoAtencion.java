/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="motivo_llamado_atencion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class MotivoLlamadoAtencion
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -1133788217704360695L;
/*  21:    */   @Column(name="id_organizacion", nullable=false)
/*  22:    */   private int idOrganizacion;
/*  23:    */   @Column(name="id_sucursal", nullable=false)
/*  24:    */   private int idSucursal;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="motivo_llamado_atencion", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="motivo_llamado_atencion")
/*  28:    */   @Column(name="id_motivo_llamado_atencion", unique=true, nullable=false)
/*  29:    */   private int idMotivoLlamadoAtencion;
/*  30:    */   @Column(name="nombre", nullable=false, length=50)
/*  31:    */   @Size(min=2, max=50)
/*  32:    */   @NotNull
/*  33:    */   private String nombre;
/*  34:    */   @Column(name="codigo", nullable=true, length=20)
/*  35:    */   @Size(max=20)
/*  36:    */   private String codigo;
/*  37:    */   @Column(name="descripcion", nullable=true, length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @Column(name="texto_motivo_llamado_atencion", nullable=true, columnDefinition="text")
/*  45:    */   private String textoMotivoLlamadoAtencion;
/*  46:    */   
/*  47:    */   public int getId()
/*  48:    */   {
/*  49: 66 */     return this.idMotivoLlamadoAtencion;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdOrganizacion()
/*  53:    */   {
/*  54: 70 */     return this.idOrganizacion;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdOrganizacion(int idOrganizacion)
/*  58:    */   {
/*  59: 74 */     this.idOrganizacion = idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdSucursal()
/*  63:    */   {
/*  64: 78 */     return this.idSucursal;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdSucursal(int idSucursal)
/*  68:    */   {
/*  69: 82 */     this.idSucursal = idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdMotivoLlamadoAtencion()
/*  73:    */   {
/*  74: 86 */     return this.idMotivoLlamadoAtencion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdMotivoLlamadoAtencion(int idMotivoLlamadoAtencion)
/*  78:    */   {
/*  79: 90 */     this.idMotivoLlamadoAtencion = idMotivoLlamadoAtencion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getNombre()
/*  83:    */   {
/*  84: 94 */     return this.nombre;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setNombre(String nombre)
/*  88:    */   {
/*  89: 98 */     this.nombre = nombre;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getCodigo()
/*  93:    */   {
/*  94:102 */     return this.codigo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setCodigo(String codigo)
/*  98:    */   {
/*  99:106 */     this.codigo = codigo;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getDescripcion()
/* 103:    */   {
/* 104:110 */     return this.descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDescripcion(String descripcion)
/* 108:    */   {
/* 109:114 */     this.descripcion = descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isActivo()
/* 113:    */   {
/* 114:118 */     return this.activo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setActivo(boolean activo)
/* 118:    */   {
/* 119:122 */     this.activo = activo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isPredeterminado()
/* 123:    */   {
/* 124:126 */     return this.predeterminado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setPredeterminado(boolean predeterminado)
/* 128:    */   {
/* 129:130 */     this.predeterminado = predeterminado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String toString()
/* 133:    */   {
/* 134:135 */     return this.nombre;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getTextoMotivoLlamadoAtencion()
/* 138:    */   {
/* 139:139 */     return this.textoMotivoLlamadoAtencion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setTextoMotivoLlamadoAtencion(String textoMotivoLlamadoAtencion)
/* 143:    */   {
/* 144:143 */     this.textoMotivoLlamadoAtencion = textoMotivoLlamadoAtencion;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MotivoLlamadoAtencion
 * JD-Core Version:    0.7.0.1
 */