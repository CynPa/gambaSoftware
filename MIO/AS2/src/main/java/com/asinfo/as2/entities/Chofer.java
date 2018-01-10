/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.persistence.Transient;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="chofer", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "licencia"})})
/*  19:    */ public class Chofer
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="chofer", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="chofer")
/*  26:    */   @Column(name="id_chofer")
/*  27:    */   private int idChofer;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="licencia", nullable=false, length=20)
/*  33:    */   @NotNull
/*  34:    */   @Size(max=20)
/*  35:    */   private String licencia;
/*  36:    */   @Column(name="nombre", nullable=false)
/*  37:    */   @Size(max=200)
/*  38:    */   private String nombre;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_transportista", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private Transportista transportista;
/*  43:    */   @Column(name="descripcion", nullable=true)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="activo", nullable=false)
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Transient
/*  51:    */   private boolean rendered;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 74 */     return this.idChofer;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdChofer()
/*  59:    */   {
/*  60: 81 */     return this.idChofer;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdChofer(int idChofer)
/*  64:    */   {
/*  65: 85 */     this.idChofer = idChofer;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70: 89 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75: 93 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80: 97 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:101 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Transportista getTransportista()
/*  89:    */   {
/*  90:105 */     return this.transportista;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setTransportista(Transportista transportista)
/*  94:    */   {
/*  95:109 */     this.transportista = transportista;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getDescripcion()
/*  99:    */   {
/* 100:113 */     return this.descripcion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setDescripcion(String descripcion)
/* 104:    */   {
/* 105:117 */     this.descripcion = descripcion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isActivo()
/* 109:    */   {
/* 110:121 */     return this.activo;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setActivo(boolean activo)
/* 114:    */   {
/* 115:125 */     this.activo = activo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isPredeterminado()
/* 119:    */   {
/* 120:129 */     return this.predeterminado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setPredeterminado(boolean predeterminado)
/* 124:    */   {
/* 125:133 */     this.predeterminado = predeterminado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getLicencia()
/* 129:    */   {
/* 130:137 */     return this.licencia;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setLicencia(String licencia)
/* 134:    */   {
/* 135:141 */     this.licencia = licencia;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getNombre()
/* 139:    */   {
/* 140:145 */     return this.nombre;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setNombre(String nombre)
/* 144:    */   {
/* 145:149 */     this.nombre = nombre;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isRendered()
/* 149:    */   {
/* 150:153 */     return this.rendered;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setRendered(boolean rendered)
/* 154:    */   {
/* 155:157 */     this.rendered = rendered;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Chofer
 * JD-Core Version:    0.7.0.1
 */