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
/*  15:    */ @Table(name="destino_costo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class DestinoCosto
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 8099197293018127192L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="destino_costo", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="destino_costo")
/*  24:    */   @Column(name="id_destino_costo")
/*  25:    */   private int idDestinoCosto;
/*  26:    */   @Column(name="id_organizacion")
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal")
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="codigo", nullable=false, length=20)
/*  31:    */   @NotNull
/*  32:    */   @Size(min=1, max=20)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=100)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=2, max=100)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="descripcion", length=200, nullable=true)
/*  39:    */   @Size(max=200)
/*  40:    */   private String descripcion;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44:    */   private boolean activo;
/*  45:    */   
/*  46:    */   public int getId()
/*  47:    */   {
/*  48: 83 */     return this.idDestinoCosto;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 93 */     return this.nombre;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdDestinoCosto()
/*  57:    */   {
/*  58: 97 */     return this.idDestinoCosto;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdDestinoCosto(int idDestinoCosto)
/*  62:    */   {
/*  63:101 */     this.idDestinoCosto = idDestinoCosto;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68:105 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73:109 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78:113 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83:117 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getCodigo()
/*  87:    */   {
/*  88:121 */     return this.codigo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCodigo(String codigo)
/*  92:    */   {
/*  93:125 */     this.codigo = codigo;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getNombre()
/*  97:    */   {
/*  98:129 */     return this.nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setNombre(String nombre)
/* 102:    */   {
/* 103:133 */     this.nombre = nombre;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getDescripcion()
/* 107:    */   {
/* 108:137 */     return this.descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDescripcion(String descripcion)
/* 112:    */   {
/* 113:141 */     this.descripcion = descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isPredeterminado()
/* 117:    */   {
/* 118:145 */     return this.predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setPredeterminado(boolean predeterminado)
/* 122:    */   {
/* 123:149 */     this.predeterminado = predeterminado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isActivo()
/* 127:    */   {
/* 128:153 */     return this.activo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setActivo(boolean activo)
/* 132:    */   {
/* 133:157 */     this.activo = activo;
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DestinoCosto
 * JD-Core Version:    0.7.0.1
 */